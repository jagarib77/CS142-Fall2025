package model.world;

import model.entities.Entity;
import model.entities.LivingEntity;
import model.enums.MoveResult;
import model.items.Item;
import util.config.SimulationConstants;

import java.util.Objects;

public class WorldGrid {
    private final Entity[][] grid;
    private final int width = SimulationConstants.WORLD_WIDTH;
    private final int height = SimulationConstants.WORLD_HEIGHT;

    public WorldGrid() {
        this.grid = new Entity[height][width];
    }

    public void set(Cell cell, Entity e) {
        if (isValid(cell.x, cell.y)) {
            grid[cell.y][cell.x] = e;
            if (e != null) e.setPosition(cell);
        }
    }

    public Entity get(Cell cell) {
        if (cell == null) return null;
        return get(cell.x, cell.y);
    }

    public Entity get(int x, int y) {
        return isValid(x, y) ? grid[y][x] : null;
    }

    public void remove(Entity e) {
        if (e != null && e.getCell() != null) {
            set(e.getCell(), null);
        }
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

    public MoveResult move(Entity entity, Cell to) {
        Objects.requireNonNull(entity, "Entity cannot be null");
        Objects.requireNonNull(to, "Target cell cannot be null");

        Cell from = entity.getCell();
        if (from == null) {
            throw new IllegalStateException("Entity has no position: " + entity);
        }

        if (!entity.isPresent()) {
            return MoveResult.ENTITY_CANNOT_MOVE;
        }

        if (!isValid(to.x, to.y)) {
            return MoveResult.OUT_OF_BOUNDS;
        }

        Entity occupant = get(to);

        if (occupant == null) {
            performMove(entity, to);
            return MoveResult.SUCCESS;
        }

        if (occupant instanceof Item) {
            performMove(entity, to);
            return MoveResult.LAND_ON_ITEM;  // auto-pickup later
        }

        if (occupant instanceof LivingEntity) {
            return MoveResult.BLOCKED_BY_LIVING;
        }

        return MoveResult.BLOCKED_BY_DESTRUCTIBLE;
    }

    private void performMove(Entity entity, Cell to) {
        Cell from = entity.getCell();
        grid[from.y][from.x] = null;
        grid[to.y][to.x] = entity;
        entity.setPosition(to);
    }

    public LivingEntity getLivingAt(int x, int y) {
        Entity e = get(x, y);
        return (e instanceof LivingEntity) ? (LivingEntity) e : null;
    }

    public <T> T getAs(int x, int y, Class<T> type) {
        Entity e = get(x, y);
        return type.isInstance(e) ? type.cast(e) : null;
    }
}