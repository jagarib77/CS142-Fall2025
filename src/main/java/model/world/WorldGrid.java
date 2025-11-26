package model.world;

import model.entities.Entity;
import util.config.SimulationConstants;

public class WorldGrid {
    private final Entity[][] grid;
    private final int width = SimulationConstants.WORLD_WIDTH;
    private final int height = SimulationConstants.WORLD_HEIGHT;

    public WorldGrid() {
        this.grid = new Entity[height][width];
    }

    public void set(int x, int y, Entity e) {
        if (isValid(x, y)) {
            grid[y][x] = e;
            if (e != null) e.setPosition(x, y);
        }
    }

    public Entity get(int x, int y) {
        return isValid(x, y) ? grid[y][x] : null;
    }

    public void remove(Entity e) {
        if (e != null) set(e.getX(), e.getY(), null);
    }

    public boolean isValid(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Entity[][] raw() {
        return grid;
    }
}