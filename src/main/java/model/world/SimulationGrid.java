package model.world;

import model.*;
import static model.config.SimulationConstants.*;
import model.entities.*;
import model.items.*;

import java.util.*;

/**
 * The game world — a 70×40 grid that holds all entities.
 * Acts as the game master: spawns entities, runs turns, removes dead, checks win condition.
 */
public final class SimulationGrid {

    private final Entity[][] grid;
    private final int width;
    private final int height;
    private final Random random = new Random();
    private boolean gameOver = false;
    private final Random rng = new Random();

    public SimulationGrid() {
        this.width  = WORLD_WIDTH;
        this.height = WORLD_HEIGHT;
        this.grid  = new Entity[height][width];
    }

    public void spawnInitialWorld() {
        spawn(Civilian.class,     SPAWN_CIVILIANS);
        spawn(Soldier.class,      SPAWN_SOLDIERS);
        spawn(CommonZombie.class, SPAWN_COMMON_ZOMBIES);
        spawn(EliteZombie.class,  SPAWN_ELITES);
        spawn(Weapon.class,       SPAWN_WEAPONS);
        spawn(Armor.class,        SPAWN_ARMORS);
        spawn(Medkit.class,       SPAWN_MEDKITS);
    }

    private <T extends Entity> void spawn(Class<T> type, int count) {
        for (int i = 0; i < count; i++) {
            Cell cell = findEmptyCell();
            if (cell != null) {
                try {
                    T entity = type.getDeclaredConstructor().newInstance();
                    set(cell.x, cell.y, entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Cell findEmptyCell() {
        for (int tries = 0; tries < 200; tries++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            if (grid[y][x] == null) {
                return new Cell(x, y);
            }
        }
        return null;
    }


    public void update() {
        if (gameOver) {
            System.out.println("Game is over!");
            return;
        }

        List<LivingEntity> livingEntities = collectAllLivingCreatures();
        sortBySpeedAndLuck(livingEntities);
        letEveryoneAct(livingEntities);
        turnInfectedIntoZombies();
        buryTheDead();
        checkForWinner();
    }

    private List<LivingEntity> collectAllLivingCreatures() {
        List<LivingEntity> list = new ArrayList<>();
        for (Entity[] row : grid) {
            for (Entity e : row) {
                if (e instanceof LivingEntity && ((LivingEntity) e).isPresent()) {
                    LivingEntity le = (LivingEntity) e;
                    list.add(le);
                }
            }
        }
        return list;
    }

    private void sortBySpeedAndLuck(List<LivingEntity> actors) {
        for (LivingEntity e : actors) {
            e.setTieBreaker(rng.nextInt());
        }

        actors.sort(new Comparator<LivingEntity>() {
            @Override
            public int compare(LivingEntity a, LivingEntity b) {
                int cmp = Integer.compare(b.getInitiative(), a.getInitiative());
                if (cmp != 0) return cmp;
                return Integer.compare(a.getTieBreaker(), b.getTieBreaker());
            }
        });
    }

    private void letEveryoneAct(List<LivingEntity> livingEntities) {
        for (LivingEntity le: livingEntities) {
            if (le.isPresent()) {
                le.act(this);
            }
        }
    }

    private void turnInfectedIntoZombies() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Entity e = grid[y][x];
                if (e instanceof Human && ((Human) e).shouldBecomeZombie()) {
                    Human human = (Human) e;
                    human.kill();
                    CommonZombie zombie = new CommonZombie();
                    replace(human, zombie);
                }
            }
        }
    }

    private void buryTheDead() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Entity e = grid[y][x];
                if (e instanceof LivingEntity && !((LivingEntity) e).isPresent()) {
                    LivingEntity le = (LivingEntity) e;
                    grid[y][x] = null;
                }
            }
        }
    }

    private void checkForWinner() {
        boolean humansLeft = false;
        boolean zombiesLeft = false;

        for (Entity[] row : grid) {
            for (Entity e : row) {
                if (e instanceof Human && ((Human) e).isPresent() && !((Human) e).shouldBecomeZombie()) {
                    Human h = (Human) e;
                    humansLeft = true;
                }
                if (e instanceof Zombie && ((Zombie) e).isPresent()) {
                    Zombie z = (Zombie) e;
                    zombiesLeft = true;
                }
            }
        }

        if (!humansLeft || !zombiesLeft) {
            gameOver = true;
            String winner = humansLeft ? "HUMANS" : "ZOMBIES";
            System.out.println("\nGAME OVER! " + winner + " WIN!");
        }
    }

    public void moveToward(int targetX, int targetY, LivingEntity entity) {
        Direction dir = Direction.toward(entity.getX(), entity.getY(), targetX, targetY);
        tryMove(entity, entity.getX() + dir.dx(), entity.getY() + dir.dy());
    }

    public void moveRandomly(LivingEntity entity) {
        Direction dir = Direction.random(random);
        tryMove(entity, entity.getX() + dir.dx(), entity.getY() + dir.dy());
    }

    private void tryMove(LivingEntity entity, int nx, int ny) {
        if (nx < 0) nx = width - 1;
        else if (nx >= width) nx = 0;

        if (ny < 0) ny = height - 1;
        else if (ny >= height) ny = 0;

        if (grid[ny][nx] == null) {
            grid[entity.getY()][entity.getX()] = null;
            set(nx, ny, entity);
        }
    }

//    private void tryMove(LivingEntity entity, int nx, int ny) {
//        nx = (nx + width) % width;
//        ny = (ny + height) % height; // wrap-around
//
//        // Force move (ignore collisions)
//        grid[entity.getY()][entity.getX()] = null;
//        set(nx, ny, entity);
//    }

    public <T extends LivingEntity> void moveTowardNearest(LivingEntity e, Class<T> target) {
        T t = findNearest(e.getX(), e.getY(), target);
        if (t != null) moveToward(t.getX(), t.getY(), e);
    }

    public <T extends LivingEntity> T findNearest(int fromX, int fromY, Class<T> type) {
        T best = null;
        int bestDist = Integer.MAX_VALUE;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Entity e = grid[y][x];
                if (type.isInstance(e) && e.isPresent()) {
                    int dist = Math.abs(x - fromX) + Math.abs(y - fromY);
                    if (dist < bestDist) {
                        bestDist = dist;
                        best = type.cast(e);
                    }
                }
            }
        }
        return best;
    }

    public List<LivingEntity> getNearbyLiving(int centerX, int centerY, int range) {
        List<LivingEntity> list = new ArrayList<>();
        for (int dy = -range; dy <= range; dy++) {
            for (int dx = -range; dx <= range; dx++) {
                int x = centerX + dx;
                int y = centerY + dy;
                if (isValid(x, y)) {
                    Entity e = grid[y][x];
                    if (e instanceof LivingEntity) {
                        LivingEntity le = (LivingEntity) e;
                        if (le.isPresent()) list.add(le);
                    }
                }
            }
        }
        return list;
    }

    public int distanceBetween(LivingEntity a, LivingEntity b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    public EliteZombie findNearestElite(int x, int y) {
        return findNearest(x, y, EliteZombie.class);
    }


    public void set(int x, int y, Entity e) {
        if (isValid(x, y)) {
            grid[y][x] = e;
            if (e != null) e.setPosition(x, y);
        }
    }

    public void replace(Entity oldEntity, Entity newEntity) {
        int x = oldEntity.getX();
        int y = oldEntity.getY();
        grid[y][x] = null;
        set(x, y, newEntity);
    }

    public boolean isValid(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public int countHumans() {
        int count = 0;
        for (Entity[] row : grid) {
            for (Entity e : row) {
                if (e instanceof Human) {
                    Human h = (Human) e;
                    if (h.isPresent() && !h.shouldBecomeZombie()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public int countZombies() {
        int count = 0;
        for (Entity[] row : grid) {
            for (Entity e : row) {
                if (e instanceof Zombie) {
                    Zombie z = (Zombie) e;
                    if (z.isPresent()) count++;
                }
            }
        }
        return count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Entity e = grid[y][x];
                char symbol = (e == null) ? '.' : e.getSymbol();
                sb.append(symbol).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public Entity get(int x, int y) {
        if (isValid(x, y)) return grid[y][x];
        return null;
    }

    public void remove(Entity e) {
        if (e != null) {
            set(e.getX(), e.getY(), null);
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int countEntitiesBySymbol(char symbol) {
        int count = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Entity e = grid[y][x];
                if (e != null && e.getSymbol() == symbol && e.isPresent()) {
                    count++;
                }
            }
        }
        return count;
    }
}