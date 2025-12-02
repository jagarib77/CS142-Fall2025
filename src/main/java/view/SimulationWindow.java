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
        Simulation sim = new Simulation();
        sim.spawnInitialWorld();

        System.out.println("=== ZOMBIE APOCALYPSE — SANG VO EDITION ===");
        System.out.println("World created. " + sim.countHumans() + " humans vs "
                + sim.countZombies() + " zombies.");
        System.out.println(sim);

        GridPanel gridPanel = new GridPanel(sim);
        StatusPanel statusPanel = new StatusPanel(sim);
        SimulationController controller = new SimulationController(sim, gridPanel, statusPanel);

        JFrame frame = new JFrame("Zombie Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(statusPanel, BorderLayout.EAST);
        frame.add(ControlPanel.create(controller, frame), BorderLayout.SOUTH);

        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        controller.stopTimer();
        System.out.println(">>> SIMULATION STARTED — PAUSED (Press P to play)");
    }
}
