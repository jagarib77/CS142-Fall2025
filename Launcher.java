package jwp;
import javax.swing.*;
import java.awt.*;

public class Launcher extends JFrame {

    Launcher() {
        this.setLayout(new BorderLayout(10, 10));

        JPanel panel5 = new JPanel();
        panel5.setPreferredSize(new Dimension(100, 100));
        panel5.setBackground(Color.yellow);
        this.add(panel5, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.GRAY);
        this.add(centerPanel, BorderLayout.CENTER);

        String[] viruses = { "Default", "Black Plague", "Ebola" };
        JComboBox<String> virusBox = new JComboBox<>(viruses);
        virusBox.setPreferredSize(new Dimension(150, 30));
        panel5.add(virusBox);

        JButton infect = new JButton("Infect");
        infect.setPreferredSize(new Dimension(250, 100));
        infect.setMaximumSize(new Dimension(250, 100));
        infect.setAlignmentX(Component.CENTER_ALIGNMENT);
        infect.setFocusable(false);
        infect.addActionListener(e -> {
            String choice = (String) virusBox.getSelectedItem();
            SimulationType type;
            switch (choice) {
                case "Default" -> type = SimulationType.DEFAULT;
                case "Black Plague" -> type = SimulationType.BLACK_PLAGUE;
                case "Ebola" -> type = SimulationType.EBOLA;
                default -> type = SimulationType.DEFAULT;
            }
            this.dispose();
            new MainFrame(type);
        });
        PlagueIncSimulator sim = new PlagueIncSimulator();

        JButton custom = new JButton("Custom Virus");
        custom.setPreferredSize(new Dimension(150,50));
        custom.setMaximumSize(new Dimension(150,50));
        custom.setFocusable(false);
        custom.setAlignmentX(Component.CENTER_ALIGNMENT);
        custom.addActionListener(e -> new createVirus(sim));

        // >>> NEW BUTTON ADDED HERE <<<
        JButton settings = new JButton("Simulator Settings");
        settings.setPreferredSize(new Dimension(150,50));
        settings.setMaximumSize(new Dimension(150,50));
        settings.setFocusable(false);
        settings.setAlignmentX(Component.CENTER_ALIGNMENT);
        settings.addActionListener(e -> new SimulatorSettings());
        // >>> END OF ADDITION <<<

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(infect);
        centerPanel.add(custom);
        centerPanel.add(settings); // Added button inserted here
        centerPanel.add(Box.createVerticalGlue());

        this.setTitle("Plague Launcher");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        ImageIcon image = new ImageIcon("Plague Icon.png");
        this.setIconImage(image.getImage());
    }
}
