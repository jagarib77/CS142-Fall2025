package service;

import model.entities.Entity;
import model.entities.LivingEntity;
import model.enums.Direction;
import model.world.Cell;
import model.world.WorldGrid;

import java.util.Random;

public class MovementService {
    private final WorldGrid grid;
    private final Random RANDOM = new Random();

    public MovementService(WorldGrid grid) {
        this.grid = grid;
    }

    public void moveToward(Cell target, LivingEntity entity) {
        Direction dir = Direction.toward(entity.getCell(), target);
        tryMove(entity, entity.getCell().move(dir));
    }

    public void moveToward(LivingEntity from, LivingEntity target) {
        if (target != null) moveToward(target.getCell(), from);
    }

    public void moveRandomly(LivingEntity entity) {
        Direction dir = Direction.random(RANDOM);
        tryMove(entity, entity.getCell().move(dir));
    }

    public void tryMove(LivingEntity entity, Cell next) {
        next = wrapAround(next);

        if (grid.get(next) == null) {
            grid.set(entity.getCell(), null);
            grid.set(next, entity);
            entity.setPosition(next);
        }
    }

    private Cell wrapAround(Cell cell) {
        int x = cell.x;
        int y = cell.y;

        if (x < 0) x = grid.getWidth() - 1;
        if (x >= grid.getWidth()) x = 0;
        if (y < 0) y = grid.getHeight() - 1;
        if (y >= grid.getHeight()) y = 0;

        return new Cell(x, y);
    }

    public <T extends LivingEntity> T findNearest(LivingEntity from, Class<T> type) {
        return findNearest(from.getCell(), type);
    }

    public <T extends LivingEntity> T findNearest(Cell from, Class<T> type) {
        T best = null;
        int bestDist = Integer.MAX_VALUE;

        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Entity e = grid.get(x, y);
                if (type.isInstance(e) && e.isPresent()) {
                    Cell pos = e.getCell();
                    int d = from.distanceTo(pos);
                    if (d < bestDist) {
                        bestDist = d;
                        best = type.cast(e);
                    }
                }
            }
        }
        return best;
    }
}