package model.entities;

import model.LivingEntity;
import model.world.SimulationGrid;

/**
 * Base class for zombies with infection spread.
 */
public abstract class Zombie extends LivingEntity {
    protected void infectNearby(SimulationGrid grid) {
        for (LivingEntity le : grid.getNearbyLiving(getX(), getY(), 1)) {
            if (le instanceof Human && Math.random() < 0.8) {
                Human h = (Human) le;
                h.infect();
            }
        }
    }
}