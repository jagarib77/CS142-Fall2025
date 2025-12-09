package jwp;

import jwp.GridSimulator;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class GridPanel extends JPanel {

    public interface GridListener {
        void gridReady(); // called when grid is created or resized
    }

    private GridListener gridListener;

    // get listener
    public GridListener getGridListener() {
        return gridListener;
    }

    // set listener
    public void setGridListener(GridListener gridListener) {
        this.gridListener = gridListener;
    }

    private static final long serialVersionUID = 1L;
    private static final Font font = new Font("Courier", Font.BOLD, 25);
    private static final int CELLSIZE = 30;

    private int gridWidth;
    private int gridHeight;
    private int leftMargin;
    private int topMargin;

    private JProgressBar cureMeter;
    public void setCureMeter(JProgressBar bar){
        this.cureMeter = bar;
    }

    private Timer timer;
    private final Map<Integer, BufferedImage> statesMap = new HashMap<>(); // stores state images
    private int[][] states; // grid array
    private int tickSpeed;

    public GridPanel(int tickSpeed) {
        this.tickSpeed = tickSpeed;
        setBackground(Color.black);
        registerState(0, Color.orange); // default state

        // updates the grid every 500ms, will be adding slider in GUI to adjust speed
        timer = new Timer(tickSpeed, e -> tick());
        timer.start();
    }

    private GridSimulator simulator;
    public void setSimulator(GridSimulator sim){
        this.simulator = sim; // set simulation logic
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // create grid if size changed
        int newW = Math.max(1, getWidth() / CELLSIZE);
        int newH = Math.max(1, getHeight() / CELLSIZE);
        if (states == null || newW != gridWidth || newH != gridHeight) {
            resizeOrInitGrid(newW, newH);
        }

        if (states == null) return;

        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        // center the grid
        int xSpare = width - (gridWidth * CELLSIZE);
        int ySpare = height - (gridHeight * CELLSIZE);
        leftMargin = xSpare / 2;
        topMargin = ySpare / 2;

        g2.setColor(Color.white);
        g2.fillRect(leftMargin, topMargin, gridWidth * CELLSIZE, gridHeight * CELLSIZE);

        // draw each cell
        for (int gy = 0; gy < gridHeight; gy++) {
            for (int gx = 0; gx < gridWidth; gx++) {
                int sx = gx * CELLSIZE + leftMargin;
                int sy = gy * CELLSIZE + topMargin;
                int state = states[gy][gx];
                BufferedImage bi = statesMap.get(state);
                if (bi != null) g2.drawImage(bi, sx + 1, sy + 1, null);
            }
        }
    }

    private void resizeOrInitGrid(int newW, int newH) {
        if (newW <= 0) newW = 1;
        if (newH <= 0) newH = 1;

        if (states == null) {
            // first time creating grid
            gridWidth = newW;
            gridHeight = newH;
            states = new int[gridHeight][gridWidth];
        } else {
            // resize and keep old data
            int oldH = states.length;
            int oldW = (oldH > 0) ? states[0].length : 0;
            int[][] next = new int[newH][newW];
            for (int y = 0; y < Math.min(oldH, newH); y++) {
                System.arraycopy(states[y], 0, next[y], 0, Math.min(oldW, newW));
            }
            gridWidth = newW;
            gridHeight = newH;
            states = next;
        }

        // notify listener
        if (gridListener != null) gridListener.gridReady();
    }

    private void tick() {
        if (states == null || statesMap.isEmpty()) return;

        // run simulator logic
        if (simulator != null && cureMeter != null){
            cureMeter.setValue((int)(simulator.getCureProgress() * 100));
            simulator.tick(states, gridWidth, gridHeight);
        }
        repaint(); // redraw grid
    }

    public void registerState(Integer state, Color background) {
        registerState(state, Color.white, background, ""); // no text
    }

    public void registerState(Integer state, Color fg, Color bg, String text) {
        // create cell image
        BufferedImage bi = new BufferedImage(CELLSIZE - 1, CELLSIZE - 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        try {
            g.setColor(bg);
            g.fillRect(0, 0, CELLSIZE - 1, CELLSIZE - 1); // draw background
            if (text != null && !text.isEmpty()) {
                g.setColor(fg);
                g.setFont(font);
                FontRenderContext frc = g.getFontRenderContext();
                TextLayout tl = new TextLayout(text, font, frc);
                Rectangle2D bounds = tl.getBounds();
                float cx = CELLSIZE / 2f - (float) bounds.getCenterX();
                float cy = CELLSIZE / 2f - (float) bounds.getCenterY();
                tl.draw(g, cx, cy); // draw text
            }
        } finally {
            g.dispose();
        }
        statesMap.put(state, bi); // save image
    }

    public void setCell(int state, int x, int y) {
        if (states == null) return;
        if (y < 0 || y >= states.length || x < 0 || x >= states[0].length) return;
        states[y][x] = state; // set specific cell state
    }
}