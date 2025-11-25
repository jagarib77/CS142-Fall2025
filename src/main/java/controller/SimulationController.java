package controller;

import model.world.SimulationGrid;
import view.GridPanel;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationController {
    private final Timer timer;
    private int delayMs = 180;  // Default speed

    public SimulationController(final SimulationGrid grid, final GridPanel view) {
        timer = new Timer(delayMs, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                grid.update();
                view.repaint();
            }
        });
        timer.start();
    }

    public void setSpeed(int delayMs) {
        this.delayMs = delayMs;
        timer.setDelay(delayMs);
    }

    public void faster() { setSpeed(Math.max(50, delayMs - 50)); }
    public void slower() { setSpeed(delayMs + 50); }

    public void stopTimer() { timer.stop(); }
    public void startTimer() { timer.start(); }
}
