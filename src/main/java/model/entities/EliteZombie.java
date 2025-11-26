package model.entities;


import static util.config.SimulationConstants.*;

/**
 * Elite zombie that leads a permanent, independent horde.
 */
public class EliteZombie extends Zombie {
    public EliteZombie() {
        maxHealth = health = ELITE_ZOMBIE_HEALTH;
        baseDamage = ELITE_ZOMBIE_DAMAGE;
        baseSpeed = ELITE_ZOMBIE_SPEED;
    }
    @Override
    public char getSymbol() {
        return ELITE_ZOMBIE_CHAR;
    }
}