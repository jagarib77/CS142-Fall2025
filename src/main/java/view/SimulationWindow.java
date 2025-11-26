package view;

import controller.SimulationController;
import model.world.Simulation;

import javax.swing.*;
import java.awt.*;

public class SimulationWindow {

    public static void launch() {
        startNewSimulation();
    }

    public static void startNewSimulation() {
        Simulation grid = new Simulation();
        grid.spawnInitialWorld();

        System.out.println("=== ZOMBIE APOCALYPSE — SANG VO EDITION ===");
        System.out.println("World created. " + grid.countHumans() + " humans vs "
                + grid.countZombies() + " zombies.");
        System.out.println(grid);

        GridPanel gridPanel = new GridPanel(grid);
        StatusPanel statusPanel = new StatusPanel(grid);
        SimulationController controller = new SimulationController(grid, gridPanel, statusPanel);

        JFrame frame = new JFrame("Zombie Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(statusPanel, BorderLayout.EAST);
        frame.add(ControlPanel.create(controller), BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
