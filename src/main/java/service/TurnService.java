package service;

import model.entities.CommonZombie;
import model.entities.Human;
import model.entities.LivingEntity;
import model.world.Cell;
import model.world.Simulation;
import model.world.WorldGrid;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static util.DebugLogger.*;

/**
 * Manages turn order, initiative, actions, infection, and cleanup.
 */
public class TurnService {
    private final WorldGrid grid;
    private final Simulation simulation;
    private int turnCounter = 0;

    public TurnService(WorldGrid grid, Simulation simulation) {
        this.grid = grid;
        this.simulation = simulation;
    }

    /**
     * Executes one complete game turn.
     */
    public void processTurn() {
        turnCounter++;
        System.out.println();
        System.out.println();
        System.out.println("=============================================================");
        turn("TURN: " + turnCounter);


        List<LivingEntity> list = collectActiveEntities();
        sortByInitiative(list);
        executeActions(list);
        transformInfectedHumans();
        cleanupDeadEntities();
        System.out.println("=============================================================");
    }

    /**
     * Collects all living, active entities from the grid.
     */
    private List<LivingEntity> collectActiveEntities() {
        List<LivingEntity> list = new ArrayList<>();

        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                LivingEntity le = grid.getLivingAt(x, y);
                if (le != null && le.isPresent()) {
                    list.add(le);
                }
            }
        }
        return list;
    }

    /**
     * Sorts entities by initiative, descending.
     */
    private void sortByInitiative(List<LivingEntity> list) {
        list.sort(Comparator.comparingInt(LivingEntity::getInitiative).reversed());
    }

    /**
     * Lets every living entity perform its behavior chain.
     */
    private void executeActions(List<LivingEntity> list) {
        for (LivingEntity le : list) {
            if (le.isPresent()) {
                le.act(simulation);
            }
        }
    }

    /**
     * Transforms humans who have been infected long enough into zombies.
     */
    private void transformInfectedHumans() {
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                Human human = grid.getAs(x, y, Human.class);
                if (human != null && human.shouldBecomeZombie()) {
                    Cell cell = new Cell(x, y);
                    human.kill();
                    grid.set(cell, new CommonZombie(cell));
                }
            }
        }
    }

    /**
     * Removes dead entities from the grid.
     */
    private void cleanupDeadEntities() {
        for (int y = 0; y < grid.getHeight(); y++) {
            for (int x = 0; x < grid.getWidth(); x++) {
                LivingEntity entity = grid.getLivingAt(x, y);
                if (entity != null && entity.isDead()) {
                    grid.set(new Cell(x, y), null);
                }
            }
        }
    }
}