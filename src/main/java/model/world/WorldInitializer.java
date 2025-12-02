package model.world;

import model.entities.*;
import model.items.Armor;
import model.items.Medkit;
import model.items.Weapon;

import java.util.Random;
import java.util.function.Function;

import static util.config.SimulationConstants.*;

public class WorldInitializer {
    private final WorldGrid grid;
    private final Random RANDOM = new Random();

    public WorldInitializer(WorldGrid grid) {
        this.grid = grid;
    }

    public void spawnInitialWorld() {
        spawn(Civilian::new, SPAWN_CIVILIANS);
        spawn(Soldier::new, SPAWN_SOLDIERS);
        spawn(CommonZombie::new, SPAWN_COMMON_ZOMBIES);
        spawn(EliteZombie::new, SPAWN_ELITES_ZOMBIES);
        spawn(Weapon::new, SPAWN_WEAPONS);
        spawn(Armor::new, SPAWN_ARMORS);
        spawn(Medkit::new, SPAWN_MEDKITS);
    }

    private <T extends Entity> void spawn(Function<Cell, T> factory, int count) {
        for (int i = 0; i < count; i++) {
            Cell cell = findEmptyCell();
            if (cell != null) {
                T entity = factory.apply(cell);
                grid.set(cell, entity);
            }
        }
    }

    private Cell findEmptyCell() {
        for (int i = 0; i < 200; i++) {
            int x = RANDOM.nextInt(grid.getWidth());
            int y = RANDOM.nextInt(grid.getHeight());
            if (grid.get(x, y) == null) {
                return new Cell(x, y);
            }
        }
        return null;
    }
}