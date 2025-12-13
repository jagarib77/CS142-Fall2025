// Created by Apollo
// Creates a GUI to initialize basic simulation parameters before running the full simulation
import javax.swing.*;
import java.awt.*;

public class SetupGUI extends JFrame {
    private JTextField boidsField;
    private JTextField sadBoidsField;
    private JTextField superBoidsField;
    private JTextField treesField;
    private JTextField carsField;
    private JTextField windowsField;

    private int numBoids = 0;
    private int numSadBoids = 0;
    private int numSuperBoids = 0;
    private int numTrees = 0;
    private int numCars = 0;
    private int numWindows = 0;
    private boolean started = false;

    // Creates the GUI and adds the fields and start button
    public SetupGUI() {
        setTitle("Simulation Setup");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel("Boids:"));
        boidsField = new JTextField("10");
        add(boidsField);

        add(new JLabel("Sad Boids:"));
        sadBoidsField = new JTextField("0");
        add(sadBoidsField);

        add(new JLabel("Super Boids:"));
        superBoidsField = new JTextField("0");
        add(superBoidsField);

        add(new JLabel("Trees:"));
        treesField = new JTextField("0");
        add(treesField);

        add(new JLabel("Cars:"));
        carsField = new JTextField("0");
        add(carsField);

        add(new JLabel("Windows:"));
        windowsField = new JTextField("0");
        add(windowsField);

        JButton startButton = new JButton("Start Simulation");
        startButton.addActionListener(e -> {
            try {
                numBoids = Integer.parseInt(boidsField.getText());
                numSadBoids = Integer.parseInt(sadBoidsField.getText());
                numSuperBoids = Integer.parseInt(superBoidsField.getText());
                numTrees = Integer.parseInt(treesField.getText());
                numCars = Integer.parseInt(carsField.getText());
                numWindows = Integer.parseInt(windowsField.getText());
                
                if (numBoids < 0 || numSadBoids < 0 || numSuperBoids < 0 || 
                    numTrees < 0 || numCars < 0 || numWindows < 0) {
                    JOptionPane.showMessageDialog(this, "Please enter non-negative numbers", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                started = true;
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(new JLabel());
        add(startButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    // Waits for the user to click Start
    public void waitForStart() {
        while (!started && isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    // Gets the number of each SimulationObject added to the setup gui
    public int getNumBoids() {
        return numBoids;
    }

    public int getNumSadBoids() {
        return numSadBoids;
    }

    public int getNumSuperBoids() {
        return numSuperBoids;
    }

    public int getNumTrees() {
        return numTrees;
    }

    public int getNumCars() {
        return numCars;
    }

    public int getNumWindows() {
        return numWindows;
    }
}
