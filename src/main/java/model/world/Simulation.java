package model.world;

import model.entities.Entity;
import model.entities.LivingEntity;
import model.entities.EliteZombie;
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
        turn.update();
        if (state.checkForWinner()) gameOver = true;
    }

    public void moveToward(int targetX, int targetY, LivingEntity entity) {
        movement.moveToward(targetX, targetY, entity);
    }

    public void moveRandomly(LivingEntity entity) {
        movement.moveRandomly(entity);
    }

    public <T extends LivingEntity> void moveTowardNearest(LivingEntity e, Class<T> target) {
        T t = movement.findNearest(e.getX(), e.getY(), target);
        if (t != null) moveToward(t.getX(), t.getY(), e);
    }

    public <T extends LivingEntity> T findNearest(int fromX, int fromY, Class<T> type) {
        return movement.findNearest(fromX, fromY, type);
    }

    public EliteZombie findNearestElite(int x, int y) {
        return movement.findNearestElite(x, y);
    }

    public List<LivingEntity> getNearbyLiving(int centerX, int centerY, int range) {
        List<LivingEntity> list = new ArrayList<>();
        int startX = Math.max(0, centerX - range);
        int endX = Math.min(getWidth() - 1, centerX + range);
        int startY = Math.max(0, centerY - range);
        int endY = Math.min(getHeight() - 1, centerY + range);
        for (int y = startY; y <= endY; y++)
            for (int x = startX; x <= endX; x++) {
                Entity e = grid.get(x, y);
                if (e instanceof LivingEntity && ((LivingEntity) e).isPresent()) {
                    LivingEntity le = (LivingEntity) e;
                    list.add(le);
                }
            }
        return list;
    }

    public int distanceBetween(LivingEntity a, LivingEntity b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    public void remove(Entity e) {
        grid.remove(e);
    }

    public Entity get(int x, int y) {
        return grid.get(x, y);
    }

    public boolean isValid(int x, int y) {
        return grid.isValid(x, y);
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