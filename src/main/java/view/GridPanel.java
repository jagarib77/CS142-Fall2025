package view;

import model.*;
import model.entities.*;
import model.world.SimulationGrid;

import javax.swing.*;
import java.awt.*;

import static model.config.SimulationConstants.*;

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
                char c = e == null ? '.' : e.getSymbol();

                g.setColor(getColor(c));
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE,
                        CELL_SIZE, CELL_SIZE);

                g.setColor(Color.WHITE);
                g.drawString(String.valueOf(c),
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

    private Color getColor(char c) {
        return switch (c) {
            case CIVILIAN_CHAR -> COLOR_CIVILIAN;
            case INFECTION_CHAR -> COLOR_INFECTION;
            case SOLDIER_CHAR -> COLOR_SOLDIER;
            case COMMON_ZOMBIE_CHAR -> COLOR_COMMON_ZOMBIE;
            case ELITE_ZOMBIE_CHAR -> COLOR_ELITE_ZOMBIE;
            case WEAPON_CHAR -> COLOR_WEAPON;
            case ARMOR_CHAR -> COLOR_ARMOR;
            case MEDKIT_CHAR -> COLOR_MEDKIT;
            default -> COLOR_DEFAULT;
        };
    }
}