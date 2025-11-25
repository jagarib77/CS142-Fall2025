package model;

import model.behavior.Action;
import model.behavior.Behavior;
import model.world.SimulationGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Base class for all living entities with health, stats, and behavior system.
 */
public abstract class LivingEntity extends Entity {
    protected int health;
    protected int maxHealth;
    protected int baseDamage;
    protected int baseDefense;
    protected int baseSpeed;
    private int damageBonus = 0;
    private int defenseBonus = 0;
    private int speedBonus = 0;
    protected final Random rng = new Random();

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return baseDamage + damageBonus;
    }

    public int getDefense() {
        return baseDefense + defenseBonus;
    }

    public int getSpeed() {
        return Math.max(1, baseSpeed + speedBonus);
    }

    public void addDamageBonus(int bonus) {
        damageBonus += bonus;
    }
    public void addDefenseBonus(int bonus) {
        defenseBonus += bonus;
    }
    public void addSpeedBonus(int bonus) {
        speedBonus += bonus;
    }

    public int getInitiative() {
        return getSpeed() * 10 + rng.nextInt(20);
    }

    public void takeDamage(int dmg) {
        if (dmg < 0) {
            health = Math.min(maxHealth, health - dmg);
        } else {
            int dmgTaken = Math.max(0, dmg - getDefense());
            health = Math.max(0, health - dmgTaken);
        }
    }

    public void attack(LivingEntity target) {
        if (target != null && target.isAlive()) {
            target.takeDamage((getDamage()));
        }
    }

    @Override
    public final boolean isAlive() {
        return health > 0;
    }

    public final boolean isDead() {
        return !isAlive();
    }

    public final void kill() {
        health = 0;
    }

    public final void act(SimulationGrid grid) {
        if (!isAlive()) return;
        for (Behavior b : getBehaviors()) {
            Action result = b.execute(this, grid);
            if (result == Action.TRANSFORM || result == Action.DEAD) break;
        }
    }

    protected List<Behavior> getBehaviors() { return new ArrayList<>(); }
}
