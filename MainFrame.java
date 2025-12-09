package jwp;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    public static int tickSpeed = 500;
    public static int frameWidth = 800;
    public static int frameHeight = 800;
    private JProgressBar cureMeter;

    public MainFrame(SimulationType type){
        this(type, 0, 0);
    }

    public MainFrame(SimulationType type, double IR, double DR){
        super("Plague");

        int width = frameWidth;
        int height = frameHeight;

        var gridPanel = new GridPanel(tickSpeed);
        PlagueIncSimulator sim = new PlagueIncSimulator();

        cureMeter = new JProgressBar(0, 100);
        cureMeter.setStringPainted(true);
        cureMeter.setValue(0);
        cureMeter.setForeground(Color.GREEN);
        JLabel cureLabel = new JLabel("Cure Meter", SwingConstants.CENTER);

        PlagueIncSimulator.registerStates(gridPanel);

        switch (type){
            case DEFAULT -> sim.setDiseaseProfile(PlagueIncSimulator.PROFILE_MILD_FLU);
            case BLACK_PLAGUE -> sim.setDiseaseProfile(PlagueIncSimulator.PROFILE_DEADLY_VIRUS);
            case EBOLA -> sim.setDiseaseProfile(PlagueIncSimulator.PROFILE_RAPID_SPREAD);
            case CustomVirus -> sim.setCustomDisease("Custom Virus", IR, DR, .8,5,20);
        }

        gridPanel.setSimulator(sim);

        JPanel south = new JPanel(new BorderLayout());
        south.add(cureLabel, BorderLayout.NORTH);
        south.add(cureMeter, BorderLayout.SOUTH);

        JPanel root = new JPanel(new BorderLayout());
        root.add(gridPanel, BorderLayout.CENTER);
        root.add(south, BorderLayout.SOUTH);

        setContentPane(root);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("Plague Icon.png");
        setIconImage(image.getImage());
        setVisible(true);
        gridPanel.setCureMeter(cureMeter);
    }
}
