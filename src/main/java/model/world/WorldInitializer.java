package model.world;

import model.entities.Entity;
import model.entities.*;
import model.items.*;

import java.util.Random;

import static util.config.SimulationConstants.*;

public class WorldInitializer {
    private final WorldGrid grid;
    private final Random rng = new Random();

    public WorldInitializer(WorldGrid grid) {
        this.grid = grid;
    }

    public void spawnInitialWorld() {
        spawn(Civilian.class, SPAWN_CIVILIANS);
        spawn(Soldier.class, SPAWN_SOLDIERS);
        spawn(CommonZombie.class, SPAWN_COMMON_ZOMBIES);
        spawn(Weapon.class, SPAWN_WEAPONS);
        spawn(Armor.class, SPAWN_ARMORS);
        spawn(Medkit.class, SPAWN_MEDKITS);
    }

    private <T extends Entity> void spawn(Class<T> type, int count) {
        for (int i = 0; i < count; i++) {
            Cell cell = findEmptyCell();
            if (cell != null) {
                try {
                    T entity = type.getDeclaredConstructor().newInstance();
                    grid.set(cell, entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Cell findEmptyCell() {
        for (int i = 0; i < 200; i++) {
            int x = rng.nextInt(grid.getWidth());
            int y = rng.nextInt(grid.getHeight());
            if (grid.get(x, y) == null) return new Cell(x, y);
        }
        return null;
    }
}