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

    public OceanGUI(Map map, List<Item> items) {
        this.map = map;
        this.items.addAll(items);


        timer = new Timer(500, e -> step());
    }

    public void step() {
        for (Item item : items) {
            Location loc = map.getLocation(item.getX(), item.getY());
            Direction d = loc.chooseRandomDirection();
            item.moveBy(d, item.getX(), item.getY());
        }
        repaint();
    }

    public void start() { timer.start(); }
    public void stop() { timer.stop(); }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        for (int r = 0; r < map.getY(); r++) {
            for (int c = 0; c < map.getX(); c++) {
                int x = c * cellSize;
                int y = r * cellSize;

                g2.setColor(Color.LIGHT_GRAY);
                g2.fillRect(x, y, cellSize, cellSize);

                Location loc = map.getLocation(c, r);
                Direction d = loc.getDirection();
//                drawArrow(g2, x, y, d);
            }
        }

        int radius = 16;
        for (Item item : items) {
            int ix = item.getX() * cellSize + cellSize / 2;
            int iy = item.getY() * cellSize + cellSize / 2;

            g2.setColor(Color.RED);
            g2.fillOval(ix - radius / 2, iy - radius / 2, radius, radius);
        }
    }

    private void drawArrow(Graphics2D g2, int cellX, int cellY, Direction d) {
        int cx = cellX + cellSize / 2;
        int cy = cellY + cellSize / 2;
        int len = cellSize / 2 - 4;
        int tx = cx + d.dx * len;
        int ty = cy + d.dy * len;

        g2.setColor(Color.BLUE);
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(cx, cy, tx, ty);

        // arrowhead
        int ax = tx;
        int ay = ty;
        int hx1 = ax - (d.dy * 6) - (d.dx * 6);
        int hy1 = ay - (d.dx * 6) + (d.dy * 6);
        int hx2 = ax - (d.dy * 6) + (d.dx * 6);
        int hy2 = ay - (d.dx * 6) - (d.dy * 6);
        g2.drawLine(ax, ay, hx1, hy1);
        g2.drawLine(ax, ay, hx2, hy2);
    }
}
