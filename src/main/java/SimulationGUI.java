/*
Jake
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SimulationGUI extends JFrame {
    private final Simulation simulation;
    private final GridPanel gridPanel;

    public SimulationGUI(Simulation sim) {
        this.simulation = sim;
        setTitle("Boid Bunch Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gridPanel = new GridPanel(simulation, this);
        add(gridPanel, BorderLayout.CENTER);

        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(2, 4));

        JButton startButton = new JButton("Start");
        JButton pauseButton = new JButton("Pause");
        JButton addBoidButton = new JButton("Add Boid");
        JButton addSuperBoidButton = new JButton("Add SuperBoid");
        JButton addSadBoidButton = new JButton("Add SadBoid");
        JButton addCarButton = new JButton("Add Car");
        JButton addTreeButton = new JButton("Add Tree");
        JButton addWindowButton = new JButton("Add Window");

        controls.add(startButton);
        controls.add(pauseButton);
        controls.add(addBoidButton);
        controls.add(addSuperBoidButton);
        controls.add(addSadBoidButton);
        controls.add(addCarButton);
        controls.add(addTreeButton);
        controls.add(addWindowButton);

        add(controls, BorderLayout.SOUTH);

        startButton.addActionListener(e -> simulation.start());
        pauseButton.addActionListener(e -> simulation.pause());

        addBoidButton.addActionListener(e -> simulation.addRandomBoid());
        addSuperBoidButton.addActionListener(e -> simulation.addRandomSuperBoid());
        addSadBoidButton.addActionListener(e -> simulation.addRandomSadBoid());
        addCarButton.addActionListener(e -> simulation.addRandomCar());
        addTreeButton.addActionListener(e -> simulation.addRandomTree());
        addWindowButton.addActionListener(e -> simulation.addRandomWindow());

        Timer timer = new Timer(16, evt -> {
            simulation.step();
            gridPanel.repaint();
        });
        timer.start();

        setSize(800, 800);
        setVisible(true);
    }
    
    public void paintGridPixel(int x, int y, int width, int height, Color color, Graphics2D g2) {
        g2.setColor(color);
        int gridSize = gridPanel.getGridSize();
        g2.fillRect(x * gridSize, y * gridSize, gridSize, gridSize);
    }

    private static class GridPanel extends JPanel {
        private Simulation simulation;
        private SimulationGUI simGUI;
        final int GRID_SIZE = 20;
        
        public GridPanel(Simulation sim, SimulationGUI simGUI) {
            this.simulation = sim;
            this.simGUI = simGUI;
            setBackground(Color.WHITE);
        }
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            
            g2.setColor(Color.LIGHT_GRAY);
            for (int x = 0; x < getWidth(); x += GRID_SIZE) {
                g2.drawLine(x, 0, x, getHeight());
            }
            for (int y = 0; y < getHeight(); y += GRID_SIZE) {
                g2.drawLine(0, y, getWidth(), y);
            }

            for (SimulationObject obj : simulation.getObjects()) {
                obj.draw(simGUI, g2);
            }
        }
        
        public int getGridSize() {
            return GRID_SIZE;
        }
    }
}