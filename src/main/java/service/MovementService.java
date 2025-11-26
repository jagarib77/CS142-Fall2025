package service;

import model.entities.Entity;
import model.entities.LivingEntity;
import model.entities.EliteZombie;
import model.world.Direction;
import model.world.WorldGrid;

import java.util.Random;

public class MovementService {
    private final WorldGrid grid;
    private final Random rng = new Random();

    public MovementService(WorldGrid grid) {
        this.grid = grid;
    }

    public void moveToward(int targetX, int targetY, LivingEntity entity) {
        Direction dir = Direction.toward(entity.getX(), entity.getY(), targetX, targetY);
        tryMove(entity, entity.getX() + dir.dx(), entity.getY() + dir.dy());
    }

    public void moveRandomly(LivingEntity entity) {
        Direction dir = Direction.random(rng);
        tryMove(entity, entity.getX() + dir.dx(), entity.getY() + dir.dy());
    }

    private void tryMove(LivingEntity entity, int nx, int ny) {
        nx = nx < 0 ? grid.getWidth() - 1 : nx >= grid.getWidth() ? 0 : nx;
        ny = ny < 0 ? grid.getHeight() - 1 : ny >= grid.getHeight() ? 0 : ny;
        if (grid.get(nx, ny) == null) {
            grid.set(entity.getX(), entity.getY(), null);
            grid.set(nx, ny, entity);
        }
    }

    public <T extends LivingEntity> T findNearest(int fromX, int fromY, Class<T> type) {
        T best = null;
        int bestDist = Integer.MAX_VALUE;
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Entity e = grid.get(x, y);
                if (type.isInstance(e) && e.isPresent()) {
                    int d = Math.abs(x - fromX) + Math.abs(y - fromY);
                    if (d < bestDist) {
                        bestDist = d;
                        best = type.cast(e);
                    }
                }
            }
        }
        return best;
    }

    public EliteZombie findNearestElite(int x, int y) {
        return findNearest(x, y, EliteZombie.class);
    }
}