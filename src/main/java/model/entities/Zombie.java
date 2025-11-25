package model.entities;

import model.LivingEntity;
import model.world.SimulationGrid;

import static model.config.SimulationConstants.INFECTION_RATE_DEFAULT;
import static model.config.SimulationConstants.SEARCH_RADIUS_DEFAULT;

/**
 * Base class for zombies with infection spread.
 */
public abstract class Zombie extends LivingEntity {
    protected double infectionRate = INFECTION_RATE_DEFAULT;

    protected void infectNearby(SimulationGrid grid) {
        for (LivingEntity le : grid.getNearbyLiving(getX(), getY(), SEARCH_RADIUS_DEFAULT)) {
            if (le instanceof Human && Math.random() < infectionRate) {
                Human h = (Human) le;
                h.infect();
            }
        }
    }
}