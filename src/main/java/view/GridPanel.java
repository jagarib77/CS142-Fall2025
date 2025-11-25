package view;

import model.*;
import model.config.EntityVisual;
import model.entities.*;
import model.world.SimulationGrid;

import javax.swing.*;
import java.awt.*;

import static model.config.SimulationConstants.*;

/**
 * Displays the 2D grid of the simulation.
 */
public class GridPanel extends JPanel {
    private final SimulationGrid grid;

    public GridPanel(SimulationGrid grid) {
        this.grid = grid;
        setPreferredSize(new Dimension(grid.getWidth() * CELL_SIZE, grid.getHeight() * CELL_SIZE));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Entity e = grid.get(x, y);
                char symbol = EntityVisual.EMPTY.getSymbol();

                if (e != null) {
                    symbol = e.getSymbol();
                }

                EntityVisual visual = EntityVisual.fromChar(symbol);

                g.setColor(visual.getColor());
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE,
                        CELL_SIZE, CELL_SIZE);

                g.setColor(Color.WHITE);
                g.drawString(String.valueOf(symbol),
                        x * CELL_SIZE + STRING_X_OFFSET,
                        y * CELL_SIZE + STRING_Y_OFFSET);
            }
        }

        if (grid.isGameOver()) {
            g.setColor(OVERLAY_COLOR);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.setColor(Color.WHITE);
            g.setFont(GAME_OVER_FONT);
            g.drawString("GAME OVER", GAME_OVER_X, GAME_OVER_Y);
        }
    }
}