package view;

import model.world.Simulation;
import util.display.EntityVisual;

import javax.swing.*;
import java.awt.*;

import static util.config.SimulationConstants.*;

/**
 * Displays entity counts and stats on the side panel.
 */
public class StatusPanel extends JPanel {
    private final Simulation grid;

    public StatusPanel(Simulation grid) {
        this.grid = grid;
        setPreferredSize(new Dimension(STATUS_PANEL_WIDTH, grid.getHeight() * CELL_SIZE));
        setBackground(Color.DARK_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(GAME_STAT_FONT);
        int y = 20;

        for (EntityVisual ev : EntityVisual.values()) {
            if (ev == EntityVisual.FLOOR) continue;

            int count = grid.countEntitiesBySymbol(ev.getSymbol());

            g.setColor(ev.getColor());
            g.fillRect(CELL_SIZE, y - CELL_SIZE, CELL_SIZE, CELL_SIZE);

            g.setColor(Color.WHITE);
            g.drawString(ev.getDisplayName() + ": " + count, 30, y);

            y += 20;
        }
    }
}
