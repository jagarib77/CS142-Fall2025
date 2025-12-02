package view;

import controller.SimulationController;
import util.DebugLogger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ControlPanel extends JPanel implements KeyListener {

    private final SimulationController controller;
    private final JFrame frame;

    private ControlPanel(SimulationController controller, JFrame frame) {
        this.controller = controller;
        this.frame = frame;

        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        setBackground(new Color(40, 40, 40));

        createButtons();
        setupKeyListener();
    }

    public static JPanel create(SimulationController controller, JFrame frame) {
        return new ControlPanel(controller, frame);
    }

    private void createButtons() {
        JButton startBtn = new JButton("Play (P)");
        JButton pauseBtn = new JButton("Pause (P)");
        JButton fasterBtn = new JButton("Faster (F)");
        JButton slowerBtn = new JButton("Slower (S)");
        JButton restartBtn = new JButton("Restart (R)");
        JButton logBtn = new JButton("Toggle Log (L)");

        styleButton(startBtn);
        styleButton(pauseBtn);
        styleButton(fasterBtn);
        styleButton(slowerBtn);
        styleButton(restartBtn);
        styleButton(logBtn);

        startBtn.addActionListener(e -> controller.startTimer());
        pauseBtn.addActionListener(e -> controller.stopTimer());
        fasterBtn.addActionListener(e -> controller.faster());
        slowerBtn.addActionListener(e -> controller.slower());
        restartBtn.addActionListener(e -> restartSimulation());
        logBtn.addActionListener(e -> toggleDebugLog());

        add(startBtn);
        add(pauseBtn);
        add(fasterBtn);
        add(slowerBtn);
        add(restartBtn);
        add(logBtn);
    }

    private void styleButton(JButton btn) {
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(70, 70, 70));
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
    }

    private void setupKeyListener() {
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char c = Character.toUpperCase(e.getKeyChar());

        if (c == 'P') {
            if (controller.isRunning()) {
                controller.stopTimer();
                System.out.println(">>> SIMULATION PAUSED (P)");
            } else {
                controller.startTimer();
                System.out.println(">>> SIMULATION RESUMED (P)");
            }
        }

        if (c == 'F') {
            controller.faster();
            System.out.println(">>> SPEED UP (F)");
        }

        if (c == 'S') {
            controller.slower();
            System.out.println(">>> SLOW DOWN (S)");
        }

        if (c == 'R') {
            restartSimulation();
        }

        if (c == 'L') {
            toggleDebugLog();
        }
    }

    private void toggleDebugLog() {
        DebugLogger.setEnabled(!DebugLogger.isEnabled());
        String status = DebugLogger.isEnabled() ? "ON" : "OFF";
        System.out.println(">>> DEBUG LOGS: " + status + " (L)");
    }

    private void restartSimulation() {
        controller.stopTimer();
        frame.dispose();
        System.out.println(">>> RESTARTING SIMULATION (R)");
        SimulationWindow.startNewSimulation();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
