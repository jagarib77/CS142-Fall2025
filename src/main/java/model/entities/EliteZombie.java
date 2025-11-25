package model.entities;

import model.config.SimulationConstants;
import model.world.SimulationGrid;

/**
 * Elite zombie that leads a permanent, independent horde.
 */
public class EliteZombie extends Zombie {
    public EliteZombie() {
        maxHealth = health = SimulationConstants.ELITE_ZOMBIE_HEALTH;
        baseDamage = SimulationConstants.ELITE_ZOMBIE_DAMAGE;
        baseSpeed = SimulationConstants.ELITE_ZOMBIE_SPEED;
    }
    @Override
    public char getSymbol() {
        return SimulationConstants.ELITE_ZOMBIE_CHAR;
    }
}