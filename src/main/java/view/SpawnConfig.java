package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static util.config.SimulationConstants.*;

public class SpawnConfig {

    private static final Map<String, Integer> spawnCounts = new HashMap<>();

    public static void showAndWait() {
        spawnCounts.put("CIVILIANS", DEFAULT_SPAWN_CIVILIANS);
        spawnCounts.put("SOLDIERS", DEFAULT_SPAWN_SOLDIERS);
        spawnCounts.put("COMMON_ZOMBIES", DEFAULT_SPAWN_COMMON_ZOMBIES);
        spawnCounts.put("ELITE_ZOMBIES", DEFAULT_SPAWN_ELITE_ZOMBIES);
        spawnCounts.put("WEAPONS", DEFAULT_SPAWN_WEAPONS);
        spawnCounts.put("ARMORS", DEFAULT_SPAWN_ARMORS);
        spawnCounts.put("MEDKITS", DEFAULT_SPAWN_MEDKITS);

        JDialog dialog = new JDialog((Frame) null, "Zombie Apocalypse - Spawn Settings", true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setLayout(new BorderLayout(20, 20));
        dialog.setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(30, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);

        addSliderWithInput(mainPanel, gbc, LABEL_FONT, INPUT_FONT, "Civilians",     0, 60, DEFAULT_SPAWN_CIVILIANS);
        addSliderWithInput(mainPanel, gbc, LABEL_FONT, INPUT_FONT, "Soldiers",      0, 30, DEFAULT_SPAWN_SOLDIERS);
        addSliderWithInput(mainPanel, gbc, LABEL_FONT, INPUT_FONT, "Common Zombies",0, 80, DEFAULT_SPAWN_COMMON_ZOMBIES);
        addSliderWithInput(mainPanel, gbc, LABEL_FONT, INPUT_FONT, "Elite Zombies", 0, 15, DEFAULT_SPAWN_ELITE_ZOMBIES);
        addSliderWithInput(mainPanel, gbc, LABEL_FONT, INPUT_FONT, "Weapons",       0, 30, DEFAULT_SPAWN_WEAPONS);
        addSliderWithInput(mainPanel, gbc, LABEL_FONT, INPUT_FONT, "Armors",        0, 25, DEFAULT_SPAWN_ARMORS);
        addSliderWithInput(mainPanel, gbc, LABEL_FONT, INPUT_FONT, "Medkits",       0, 30, DEFAULT_SPAWN_MEDKITS);

        JButton startButton = new JButton("START SIMULATION");
        startButton.setFont(LABEL_FONT);
        startButton.setBackground(new Color(0, 150, 0));
        startButton.setForeground(Color.WHITE);
        startButton.setPreferredSize(new Dimension(300, 50));
        startButton.addActionListener(e -> dialog.dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(30, 30, 40));
        bottomPanel.add(startButton);

        dialog.add(mainPanel, BorderLayout.CENTER);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    private static void addSliderWithInput(
            JPanel panel, GridBagConstraints gbc,
            Font labelFont, Font inputFont,
            String name, int min, int max, int initial) {

        // Create a panel for label + input + slider
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(new Color(30, 30, 40));

        // Label + Text Field
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        leftPanel.setBackground(new Color(30, 30, 40));

        JLabel label = new JLabel(name + ":");
        label.setFont(labelFont);
        label.setForeground(Color.WHITE);
        leftPanel.add(label);

        JTextField input = new JTextField(String.valueOf(initial), 4);
        input.setFont(inputFont);
        input.setHorizontalAlignment(JTextField.CENTER);
        input.setBackground(new Color(60, 60, 70));
        input.setForeground(Color.WHITE);
        input.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100)));
        leftPanel.add(input);

        // Slider
        JSlider slider = new JSlider(min, max, initial);
        slider.setMajorTickSpacing((max - min) / 5);
        slider.setMinorTickSpacing((max - min) / 10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBackground(new Color(30, 30, 40));
        slider.setForeground(Color.WHITE);

        // SYNC: Slider → Text Field
        slider.addChangeListener(e -> {
            int value = slider.getValue();
            input.setText(String.valueOf(value));
            spawnCounts.put(name.toUpperCase().replace(" ", "_"), value);
        });

        // SYNC: Text Field → Slider (with validation)
        input.addActionListener(e -> updateFromInput(input, slider, min, max));
        input.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                updateFromInput(input, slider, min, max);
            }
        });

        row.add(leftPanel, BorderLayout.WEST);
        row.add(slider, BorderLayout.CENTER);

        panel.add(row, gbc);
        panel.add(Box.createVerticalStrut(15), gbc);
    }

    private static void updateFromInput(JTextField input, JSlider slider, int min, int max) {
        try {
            int value = Integer.parseInt(input.getText().trim());
            value = Math.max(min, Math.min(max, value));  // clamp
            slider.setValue(value);
            input.setText(String.valueOf(value));
            // Update spawn count
            String name = ((JLabel)((JPanel)input.getParent()).getComponent(0)).getText();
            name = name.replace(":", "").trim(); // extract name
            spawnCounts.put(name.toUpperCase().replace(" ", "_"), value);
        } catch (NumberFormatException ex) {
            input.setText(String.valueOf(slider.getValue()));
        }
    }

    public static int getCivilians()     { return spawnCounts.getOrDefault("CIVILIANS", DEFAULT_SPAWN_CIVILIANS); }
    public static int getSoldiers()      { return spawnCounts.getOrDefault("SOLDIERS", DEFAULT_SPAWN_SOLDIERS); }
    public static int getCommonZombies() { return spawnCounts.getOrDefault("COMMON_ZOMBIES", DEFAULT_SPAWN_COMMON_ZOMBIES); }
    public static int getEliteZombies()  { return spawnCounts.getOrDefault("ELITE_ZOMBIES", DEFAULT_SPAWN_ELITE_ZOMBIES); }
    public static int getWeapons()       { return spawnCounts.getOrDefault("WEAPONS", DEFAULT_SPAWN_WEAPONS); }
    public static int getArmors()        { return spawnCounts.getOrDefault("ARMORS", DEFAULT_SPAWN_ARMORS); }
    public static int getMedkits()       { return spawnCounts.getOrDefault("MEDKITS", DEFAULT_SPAWN_MEDKITS); }
}