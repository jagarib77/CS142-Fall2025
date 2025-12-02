package model.world;

import model.entities.Entity;
import model.entities.LivingEntity;
import service.GameStateService;
import service.MovementService;
import service.TurnService;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final WorldGrid grid;
    private final WorldInitializer init;
    private final MovementService movement;
    private final TurnService turn;
    private final GameStateService state;
    private boolean gameOver = false;

    public Simulation() {
        this.grid = new WorldGrid();
        this.init = new WorldInitializer(grid);
        this.movement = new MovementService(grid);
        this.turn = new TurnService(grid, this);
        this.state = new GameStateService(grid);
    }

    public void spawnInitialWorld() {
        init.spawnInitialWorld();
    }

    public void update() {
        if (gameOver) return;
        turn.processTurn();
        if (state.checkForWinner()) gameOver = true;
    }

    public void moveToward(Cell target, LivingEntity entity) {
        movement.moveToward(target, entity);
    }

    public void moveToward(LivingEntity target, LivingEntity mover) {
        if (target != null && target.getCell() != null) {
            moveToward(target.getCell(), mover);
        }
    }

    public void moveAwayFrom(LivingEntity mover, Entity avoid) {
        if (avoid == null || avoid.getCell() == null) return;

        int dx = mover.getX() - avoid.getX();
        int dy = mover.getY() - avoid.getY();
        Cell away = new Cell(
                mover.getX() + Integer.signum(dx),
                mover.getY() + Integer.signum(dy));

        if (isValid(away)) {
            moveToward(away, mover);
        }
    }

    public void moveRandomly(LivingEntity entity) {
        movement.moveRandomly(entity);
    }

    public <T extends LivingEntity> void moveTowardNearest(LivingEntity entity, Class<T> targetType) {
        T nearest = movement.findNearest(entity, targetType);
        if (nearest != null) {
            moveToward(nearest.getCell(), entity);
        }
    }

    public <T extends LivingEntity> T findNearest(Cell from, Class<T> type) {
        return movement.findNearest(from, type);
    }

    public List<LivingEntity> getNearbyLiving(Cell center, int range) {
        return getNearbyLiving(center.x, center.y, range);
    }

    private List<LivingEntity> getNearbyLiving(int centerX, int centerY, int range) {
        List<LivingEntity> result = new ArrayList<>();
        int startX = Math.max(0, centerX - range);
        int endX   = Math.min(getWidth() - 1, centerX + range);
        int startY = Math.max(0, centerY - range);
        int endY   = Math.min(getHeight() - 1, centerY + range);

        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                Entity e = grid.get(x, y);
                if (e instanceof LivingEntity && ((LivingEntity) e).isPresent()) {
                    LivingEntity le = (LivingEntity) e;
                    result.add(le);
                }
            }
        }
        return result;
    }

    public boolean isValid(Cell cell) {
        if (cell == null) return false;
        return cell.x >= 0 && cell.y >= 0
                && cell.x < getWidth()
                && cell.y < getHeight();
    }

    public boolean isValid(int x, int y) {
        return x >= 0 && y >= 0
                && x < getWidth()
                && y < getHeight();
    }

    public void remove(Entity entity) {
        grid.remove(entity);
    }

    public Entity get(Cell cell) {
        return cell != null ? grid.get(cell) : null;
    }

    public int getWidth() {
        return grid.getWidth();
    }

    public int getHeight() {
        return grid.getHeight();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getWinner() {
        return state.getWinner();
    }

    public int countHumans() {
        return state.countHumans();
    }

    public int countZombies() {
        return state.countZombies();
    }

    public int countEntitiesBySymbol(char symbol) {
        return state.countEntitiesBySymbol(symbol);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Entity e = grid.get(x, y);
                sb.append(e == null ? '.' : e.getSymbol()).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}