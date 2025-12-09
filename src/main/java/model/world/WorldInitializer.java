package model.world;

import model.entities.*;
import model.items.*;
import view.SpawnConfig;

import java.util.Random;
import java.util.function.Function;


public class WorldInitializer {
    private final WorldGrid grid;
    private final Random RANDOM = new Random();

    public WorldInitializer(WorldGrid grid) {
        this.grid = grid;
    }

    public void spawnInitialWorld() {
        spawn(SpawnConfig.getCivilians(),     Civilian::new);
        spawn(SpawnConfig.getSoldiers(),      Soldier::new);
        spawn(SpawnConfig.getCommonZombies(), CommonZombie::new);
        spawn(SpawnConfig.getEliteZombies(),  EliteZombie::new);
        spawn(SpawnConfig.getWeapons(),       Weapon::new);
        spawn(SpawnConfig.getArmors(),        Armor::new);
        spawn(SpawnConfig.getMedkits(),       Medkit::new);
    }

    private <T extends Entity> void spawn(int count, Function<Cell, T> factory) {
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