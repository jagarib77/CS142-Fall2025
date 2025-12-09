/*
CS 142
AUTHORS: Thaknin Hor, Afnan Ali
12/07/2025
*/

package OceanPath;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OceanGUI extends JPanel {

    private final Map map;
    private final List<Item> items = new ArrayList<>();
    private final int cellSize = 20;
    private Timer timer;
    private final Color itemColor = new Color(170, 170, 170); // dark gray

    public OceanGUI(Map map, List<Item> items) {
        this.map = map;
        this.items.addAll(items);

        setPreferredSize(new Dimension(map.getX() * cellSize, map.getY() * cellSize));
        setBackground(new Color(173, 216, 230));

        timer = new Timer(500, e -> step());
    }

    public void setSpeed(int ms) {
        timer.setDelay(ms);
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void step() {
        for (Item item : items) {
            Location loc = map.getLocation(item.getX(), item.getY());
            Direction d = loc.chooseRandomDirection();
            item.moveBy(d, map.getY(), map.getX());
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (int r = 0; r < map.getY(); r++) {
            for (int c = 0; c < map.getX(); c++) {
                int x = c * cellSize;
                int y = r * cellSize;

                g.setColor(new Color(173, 216, 230));  // light blue
                g2.fillRect(x, y, cellSize, cellSize);

                Location loc = map.getLocation(c, r);
                Direction d = loc.getDirection();
                if (d != null) {
                    drawArrow(g2, x, y, d);
                }
            }
        }

        int radius = 14;
        g.setColor(itemColor);  


        for (Item item : items) {
            int ix = item.getX() * cellSize + cellSize / 2;
            int iy = item.getY() * cellSize + cellSize / 2;
            g2.fillOval(ix - radius / 2, iy - radius / 2, radius, radius);
        }
    }

    private void drawArrow(Graphics2D g2, int cellX, int cellY, Direction d) {
        int cx = cellX + cellSize / 2;
        int cy = cellY + cellSize / 2;

        int len = cellSize / 2 - 3;
        int tx = cx + d.dx * len;
        int ty = cy + d.dy * len;

        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(2f));
        g2.drawLine(cx, cy, tx, ty);

        // Arrowhead
        int ah = 6;
        int ax1 = tx - d.dy * ah - d.dx * ah;
        int ay1 = ty - d.dx * ah + d.dy * ah;

        int ax2 = tx - d.dy * ah + d.dx * ah;
        int ay2 = ty - d.dx * ah - d.dy * ah;

        g2.drawLine(tx, ty, ax1, ay1);
        g2.drawLine(tx, ty, ax2, ay2);
    }
}
