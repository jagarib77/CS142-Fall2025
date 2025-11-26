package controller;

import model.world.Simulation;
import view.GridPanel;
import view.StatusPanel;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationController {
    private final Timer timer;
    private int delayMs = 180;

    public SimulationController(Simulation grid, GridPanel gridPanel, StatusPanel statusPanel) {
        timer = new Timer(delayMs, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!grid.isGameOver()) {
                    grid.update();
                    gridPanel.repaint();
                    statusPanel.repaint();
                } else {
                    stopTimer();
                }
            }
        });
        timer.start();
    }


    public void setSpeed(int delayMs) {
        this.delayMs = delayMs;
        timer.setDelay(delayMs);
    }

    public void faster() {
        setSpeed(Math.max(50, delayMs - 50));
    }

    public void slower() {
        setSpeed(delayMs + 50);
    }

    public void stopTimer() {
        timer.stop();
    }

    public void startTimer() {
        timer.start();
    }
}
