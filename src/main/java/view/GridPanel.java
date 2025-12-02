package view;

import model.entities.Entity;
import model.world.Cell;
import model.world.Simulation;
import util.display.EntityVisual;

import javax.swing.*;
import java.awt.*;

import static util.config.SimulationConstants.*;

/**
 * Displays the 2D grid of the simulation.
 */
public class GridPanel extends JPanel {

    private final Simulation grid;

    public GridPanel(Simulation grid) {
        this.grid = grid;
        setPreferredSize(new Dimension(grid.getWidth() * CELL_SIZE, grid.getHeight() * CELL_SIZE));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGrid(g);

        if (grid.isGameOver()) {
            drawGameOverOverlay(g);
        }
    }

    /**
     * Draw all cells and entities on the grid
     */
    private void drawGrid(Graphics g) {
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                drawCell(g, x, y);
            }
        }
    }

    /**
     * Draw a single cell and its symbol
     */
    private void drawCell(Graphics g, int x, int y) {
        Entity e = grid.get(new Cell(x, y));
        char symbol = (e != null) ? e.getSymbol() : EntityVisual.FLOOR.getSymbol();
        EntityVisual visual = EntityVisual.fromChar(symbol);

        g.setColor(visual.getColor());
        g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(symbol),
                x * CELL_SIZE + STRING_X_OFFSET,
                y * CELL_SIZE + STRING_Y_OFFSET);
    }

    /**
     * Draw a centered GAME OVER overlay
     */
    private void drawGameOverOverlay(Graphics g) {
        g.setColor(OVERLAY_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw centered text
        g.setColor(Color.WHITE);
        g.setFont(GAME_OVER_FONT);

        String message = "GAME OVER";
        if (grid.getWinner() != null) {
            message = grid.getWinner();
        }
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(message);
        int textHeight = fm.getAscent();

        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2;

        g.drawString(message, x, y);
    }
}
