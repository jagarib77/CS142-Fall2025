import controller.SimulationController;
import model.world.SimulationGrid;
import view.GridPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        startNewGame();
    }

    public static void startNewGame() {
        SimulationGrid grid = new SimulationGrid();
        grid.spawnInitialWorld();

        System.out.println("=== ZOMBIE APOCALYPSE — SANG VO EDITION ===");
        System.out.println("World created. " + grid.countHumans() + " humans vs " + grid.countZombies() + " zombies.");
        System.out.println(grid);

        GridPanel panel = new GridPanel(grid);
        SimulationController controller = new SimulationController(grid, panel);


        JFrame frame = new JFrame("Zombie Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(panel, BorderLayout.CENTER);
        frame.add(createControlPanel(controller), BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel createControlPanel(SimulationController controller) {
        JPanel controls = new JPanel();
        controls.setLayout(new FlowLayout());

        JButton startBtn = new JButton("Start");
        JButton stopBtn = new JButton("Stop");
        JButton fasterBtn = new JButton("Faster");
        JButton slowerBtn = new JButton("Slower");
        JButton restartBtn = new JButton("Restart");

        controls.add(startBtn);
        controls.add(stopBtn);
        controls.add(fasterBtn);
        controls.add(slowerBtn);
        controls.add(restartBtn);

        // Add action listeners without lambdas
        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.startTimer();
            }
        });

        stopBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.stopTimer();
            }
        });

        fasterBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.faster();
            }
        });

        slowerBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.slower();
            }
        });

        restartBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.stopTimer();
                startNewGame(); // restart the simulation
            }
        });

        return controls;
    }

}
