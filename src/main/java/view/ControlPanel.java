package view;

import controller.SimulationController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(restartBtn);
                frame.dispose();

                SimulationWindow.startNewSimulation();
            }
        });

        return controls;
    }
}
