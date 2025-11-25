package view;

import model.*;
import model.entities.*;
import model.world.SimulationGrid;
import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel {
    private static final int CELL = 12;
    private final SimulationGrid grid;

    public GridPanel(SimulationGrid grid) {
        this.grid = grid;
        setPreferredSize(new Dimension(grid.getWidth() * CELL, grid.getHeight() * CELL));
        setBackground(Color.BLACK);
    }

    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Entity e = grid.get(x, y);
                char c = e == null ? '.' : e.getSymbol();
                g.setColor(getColor(c, e));
                g.fillRect(x * CELL, y * CELL, CELL, CELL);
                g.setColor(Color.WHITE);
                g.drawString(String.valueOf(c), x * CELL + 3, y * CELL + 10);
            }
        }
        if (grid.isGameOver()) {
            g.setColor(new Color(0,0,0,200));
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("GAME OVER", 150, 200);
        }
    }

    private Color getColor(char c, Entity e) {
        if (c == 'Z' && e != null) {
            EliteZombie leader = grid.findNearestElite(e.getX(), e.getY());
            if (leader != null) {
                int seed = leader.getX() + leader.getY() * 1000;
                return switch (seed % 5) {
                    case 0 -> new Color(200, 0, 0);
                    case 1 -> new Color(150, 0, 150);
                    case 2 -> new Color(255, 100, 0);
                    case 3 -> new Color(0, 150, 150);
                    default -> new Color(100, 255, 0);
                };
            }
        }

        return switch (c) {
            case 'C', 'S', 'I' -> new Color(100, 180, 255);
            case 'E' -> Color.MAGENTA;
            case 'W' -> Color.ORANGE;
            case 'A' -> Color.LIGHT_GRAY;
            case 'M' -> Color.GREEN;
            default -> Color.DARK_GRAY;
        };
    }
}