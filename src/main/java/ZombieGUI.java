import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
//This is the ZombieInvasionGUI
//ONLY DISPLAY/GRAPHICAL CHANGES
//this class stores and holds user input as well as
public class ZombieGUI {

    public static Map<String, Object> showSettingsDialog(Frame parent, String title,
                                                         String[] names, Class<?>[] types,
                                                         Object[] initialValues) {
        JDialog dialog = new JDialog(parent, title, true);
        dialog.setLayout(new BorderLayout());

        JPanel labelPanel = new JPanel(new GridLayout(names.length, 1, 5, 5));
        JPanel inputPanel = new JPanel(new GridLayout(names.length, 1, 5, 5));
        JComponent[] inputs = new JComponent[names.length];

        for (int i = 0; i < names.length; i++) {
            labelPanel.add(new JLabel(names[i]));

            if (types[i] == Boolean.TYPE) {
                JCheckBox checkBox = new JCheckBox();
                if (initialValues != null && initialValues[i] instanceof Boolean) {
                    checkBox.setSelected((Boolean) initialValues[i]);
                }
                inputs[i] = checkBox;
                inputPanel.add(checkBox);

            } else if (types[i] == Integer.TYPE || types[i] == Double.TYPE || types[i] == String.class) {
                JTextField textField = new JTextField(10);
                if (initialValues != null && initialValues[i] != null) {
                    textField.setText(initialValues[i].toString());
                }
                inputs[i] = textField;
                inputPanel.add(textField);

            } else {
                inputPanel.add(new JPanel()); // empty panel for unknown types
            }
        }

        dialog.add(labelPanel, BorderLayout.WEST);
        dialog.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        final Map<String, Object>[] result = new Map[]{null};

        okButton.addActionListener((ActionEvent e) -> {
            Map<String, Object> map = new HashMap<>();
            try {
                for (int i = 0; i < names.length; i++) {
                    if (types[i] == Boolean.TYPE) {
                        map.put(names[i], ((JCheckBox) inputs[i]).isSelected());
                    } else if (types[i] == Integer.TYPE) {
                        map.put(names[i], Integer.parseInt(((JTextField) inputs[i]).getText()));
                    } else if (types[i] == Double.TYPE) {
                        map.put(names[i], Double.parseDouble(((JTextField) inputs[i]).getText()));
                    } else if (types[i] == String.class) {
                        map.put(names[i], ((JTextField) inputs[i]).getText());
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid input: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
                return; // don't close the dialog
            }
            result[0] = map;
            openGridWindow(map);
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> {
            result[0] = null;
            dialog.dispose();
        });
        //adds buttons to JPanel(buttonPanel)
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        return result[0];
    }
    // method that creates JPanel as a grid for Zombie Simulation
    private static void openGridWindow(Map<String, Object> settings) {
        int width = (int) settings.getOrDefault("Map Width", 5);
        int height = (int) settings.getOrDefault("Map Height", 5);

        JFrame gridFrame = new JFrame("Zombie Invasion Simulation");
        gridFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gridFrame.setLayout(new BorderLayout());
        // NOTE: --------------Model + Day Counter------------------
        final int[] day = {0};

        JLabel dayLabel = new JLabel("Day: 0");
        dayLabel.setFont(new Font("Arial", Font.BOLD, 16));


        // NOTE: ----------------Grid Panel----------------
        JPanel gridPanel = new JPanel(new GridLayout(height, width));
        for (int i = 0; i < height * width; i++) {
            JPanel cell = new JPanel();
            cell.setBackground(Color.WHITE);
            cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            gridPanel.add(cell);
        }

        gridFrame.add(gridPanel, BorderLayout.CENTER);
        //updates simulation every 500ms (change if needed)
        Timer timer = new Timer(500, e -> {
            day[0]++;                 // increment day
            dayLabel.setText("Day: " + day[0]);

            // TODO: call your model.updateTick() here when model class is ready. dont forget
            //ZombieInvasionModel.updateTick();
            // redrawsw the grid
            gridPanel.repaint();
        });
        // NOTE: ----------------Legend Panel----------------
        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(new BoxLayout(legendPanel, BoxLayout.Y_AXIS));
        legendPanel.setBorder(BorderFactory.createTitledBorder("Legend"));

        legendPanel.add(createLegendItem(Color.RED, "Zombie"));
        legendPanel.add(createLegendItem(Color.GREEN, "Human"));
        legendPanel.add(createLegendItem(Color.YELLOW, "Medic"));
        // NOTE: need to create another toggle Legend for ChosenOne if buttonSwitch is on

        gridFrame.add(legendPanel, BorderLayout.EAST);

        // NOTE: ----------------Panel Default Settings----------------
        gridFrame.pack();
        gridFrame.setSize(Math.min(width * 40 + 150, 1000), Math.min(height * 40 + 50, 800));
        gridFrame.setLocationRelativeTo(null);
        gridFrame.setVisible(true);
        // NOTE: ----------------Control Panel----------------
        JButton startButton = new JButton("Start");
        JButton pauseButton = new JButton("Pause");
        JButton quitButton = new JButton("Quit");

        // NOTE: ---------------ACTION LISTENERS(buttons)-------------
        startButton.addActionListener(e -> timer.start());
        pauseButton.addActionListener(e -> timer.stop());
        quitButton.addActionListener(e -> gridFrame.dispose());

        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(startButton);
        controlPanel.add(pauseButton);
        controlPanel.add(dayLabel);
        controlPanel.add(quitButton);

        gridFrame.add(controlPanel, BorderLayout.SOUTH);
    }

    // Helper method to create a color + label legend item
    //Color-coded scheme for the blocks on grid
    private static JPanel createLegendItem(Color color, String label) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JPanel colorBox = new JPanel();
        colorBox.setBackground(color);
        colorBox.setPreferredSize(new Dimension(20, 20));
        colorBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel text = new JLabel(label);

        panel.add(colorBox);
        panel.add(text);
        return panel;
    }

    // main method creates values and invokes swing utilites
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] names = {"Enable ChosenOne", "Map Width", "Map Height", "Infection Rate", "Heal Rate", "Number of Zombies", "Number of Medic"};
            Class<?>[] types = {Boolean.TYPE, Integer.TYPE,Integer.TYPE, Integer.TYPE,Integer.TYPE, Integer.TYPE, Integer.TYPE};
            Object[] defaults = {false, 50, 50, 25, 15,1,5};

            Map<String, Object> settings = showSettingsDialog(null, "Simulation Settings",
                    names, types, defaults);

            if (settings != null) {
                settings.forEach((k, v) -> System.out.println(k + " = " + v));
            } else {
                System.out.println("Settings canceled.");
            }
        });
    }
}