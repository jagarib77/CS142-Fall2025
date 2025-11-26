package service;

import model.entities.Entity;
import model.entities.LivingEntity;
import model.entities.*;
import model.world.Cell;
import model.world.Simulation;
import model.world.WorldGrid;

import java.util.*;

public class TurnService {
    private final WorldGrid grid;
    private final Simulation simulation;
    private final Random rng = new Random();

    public TurnService(WorldGrid grid, Simulation simulation) {
        this.grid = grid;
        this.simulation = simulation;
    }

    public void update() {
        List<LivingEntity> actors = collectLiving();
        sortByInitiative(actors);
        letEveryoneAct(actors);
        transformInfected();
        removeDead();
    }

    private List<LivingEntity> collectLiving() {
        List<LivingEntity> list = new ArrayList<>();
        for (Entity[] row : grid.raw())
            for (Entity e : row)
                if (e instanceof LivingEntity && ((LivingEntity) e).isPresent()) {
                    LivingEntity le = (LivingEntity) e;
                    list.add(le);
                }
        return list;
    }

    private void sortByInitiative(List<LivingEntity> actors) {
        for (LivingEntity e : actors) e.setTieBreaker(rng.nextInt());
        actors.sort((a, b) -> {
            int cmp = Integer.compare(b.getInitiative(), a.getInitiative());
            return cmp != 0 ? cmp : Integer.compare(a.getTieBreaker(), b.getTieBreaker());
        });
    }

    private void letEveryoneAct(List<LivingEntity> actors) {
        for (LivingEntity le : actors)
            if (le.isPresent())
                le.act(simulation);
    }

    private void transformInfected() {
        for (int y = 0; y < grid.getHeight(); y++)
            for (int x = 0; x < grid.getWidth(); x++) {
                Entity e = grid.get(x, y);
                if (e instanceof Human && ((Human) e).shouldBecomeZombie()) {
                    Human h = (Human) e;
                    h.kill();
                    grid.set(new Cell(x, y), new CommonZombie());
                }
            }
    }

    private void removeDead() {
        for (int y = 0; y < grid.getHeight(); y++)
            for (int x = 0; x < grid.getWidth(); x++)
                if (grid.get(x, y) instanceof LivingEntity && !((LivingEntity) grid.get(x, y)).isPresent()) {
                    LivingEntity le = (LivingEntity) grid.get(x, y);
                    grid.set(new Cell(x, y), new CommonZombie());
                }
    }
}