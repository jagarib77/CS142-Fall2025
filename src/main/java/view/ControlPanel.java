package view;

import controller.SimulationController;

import javax.swing.*;
import java.awt.*;

public class ControlPanel {

    public static JPanel create(SimulationController controller) {
        JPanel controls = new JPanel(new FlowLayout());

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

        startBtn.addActionListener(e -> controller.startTimer());
        stopBtn.addActionListener(e -> controller.stopTimer());
        fasterBtn.addActionListener(e -> controller.faster());
        slowerBtn.addActionListener(e -> controller.slower());
        restartBtn.addActionListener(e -> {
            controller.stopTimer();

            Frame frame = (JFrame) SwingUtilities.getWindowAncestor(restartBtn);
            frame.dispose();

            SimulationWindow.startNewSimulation();
        });

        return controls;
    }
}
