/*
Jake
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulationGUI extends JFrame {
    private static final int GRID_SIZE = 10;
    private final Simulation simulation;
    private final GridPanel gridPanel;

    public SimulationGUI(Simulation sim) {
        this.simulation = sim;
        setTitle("Boid Bunch Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gridPanel = new GridPanel(simulation, this, GRID_SIZE);
        add(gridPanel, BorderLayout.CENTER);

        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BorderLayout());

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(2, 4));

        buttons.add(createButton("Start", e -> simulation.start()));
        buttons.add(createButton("Pause", e -> simulation.pause()));
        buttons.add(createHoldButton("Add Boid", () -> simulation.addRandomBoid()));
        buttons.add(createHoldButton("Add SuperBoid", () -> simulation.addRandomSuperBoid()));
        buttons.add(createHoldButton("Add SadBoid", () -> simulation.addRandomSadBoid()));
        buttons.add(createHoldButton("Add Car", () -> simulation.addRandomCar()));
        buttons.add(createHoldButton("Add Tree", () -> simulation.addRandomTree()));
        buttons.add(createHoldButton("Add Window", () -> simulation.addRandomWindow()));

        JPanel speedPanel = new JPanel();
        speedPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        JLabel speedLabel = new JLabel("Speed Multiplier:");
        JSlider speedSlider = new JSlider(0, 20, 10);
        JTextField speedText = new JTextField("1.0", 5);
        
        speedSlider.addChangeListener(e -> {
            double speed = speedSlider.getValue() / 10.0;
            speedText.setText(String.format("%.1f", speed));
            simulation.setSpeedMultiplier(speed);
        });
        
        speedText.addActionListener(e -> {
            try {
                double speed = Double.parseDouble(speedText.getText());
                if(speed >= 0.0 && speed <= 2.0) {
                    speedSlider.setValue((int)(speed * 10));
                    simulation.setSpeedMultiplier(speed);
                } else {
                    speedText.setText(String.format("%.1f", speedSlider.getValue() / 10.0));
                }
            } catch(NumberFormatException ex) {
                speedText.setText(String.format("%.1f", speedSlider.getValue() / 10.0));
            }
        });
        
        speedPanel.add(speedLabel);
        speedPanel.add(speedSlider);
        speedPanel.add(speedText);
        
        controlsPanel.add(buttons, BorderLayout.CENTER);
        controlsPanel.add(speedPanel, BorderLayout.SOUTH);

        add(controlsPanel, BorderLayout.SOUTH);

        Timer timer = new Timer(16, evt -> {
            simulation.step();
            gridPanel.repaint();
        });
        timer.start();

        setSize(800, 800);
        setVisible(true);
    }
    
    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        return button;
    }
    
    private JButton createHoldButton(String text, Runnable action) {
        JButton button = new JButton(text);
        Timer holdTimer = new Timer(100, e -> action.run());
        holdTimer.setInitialDelay(0);
        
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                holdTimer.start();
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                holdTimer.stop();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                holdTimer.stop();
            }
        });
        
        return button;
    }
    
    public void paintGridPixel(int x, int y, int width, int height, Color color, Graphics2D g2) {
        g2.setColor(color);
        int gridSize = gridPanel.getGridSize();
        g2.fillRect(x * gridSize, y * gridSize, width * gridSize, height * gridSize);
    }

    private static class GridPanel extends JPanel {
        private Simulation simulation;
        private SimulationGUI simGUI;
        private final int GRID_SIZE;
        
        public GridPanel(Simulation sim, SimulationGUI simGUI, int gridSize) {
            this.simulation = sim;
            this.simGUI = simGUI;
            this.GRID_SIZE = gridSize;
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(simulation.getCols() * GRID_SIZE, simulation.getRows() * GRID_SIZE));
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