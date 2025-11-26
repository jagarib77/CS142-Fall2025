package model.entities;

import model.LivingEntity;
import model.world.Simulation;

import static util.config.SimulationConstants.INFECTION_RATE_DEFAULT;

/**
 * Base class for zombies with infection spread.
 */
public abstract class Zombie extends LivingEntity {
    protected double infectionRate = INFECTION_RATE_DEFAULT;

    protected boolean infectNearby(Simulation grid) {
        boolean infectedSomeone = false;
        for (LivingEntity le : grid.getNearbyLiving(getX(), getY(), 1)) {
            if (le instanceof Human && Math.random() < infectionRate) {
                ((Human) le).infect();
                infectedSomeone = true;
            }
        }
        return infectedSomeone;
    }

}