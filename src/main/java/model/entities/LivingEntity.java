package model.entities;

import model.entities.behavior.Action;
import model.entities.behavior.Behavior;
import model.world.Simulation;

import java.util.ArrayList;
import java.util.List;

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
    private int tieBreaker;

    protected LivingEntity() {
        super(null);
    }

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
        return getSpeed() * 10;
    }

    public int getTieBreaker() {
        return tieBreaker;
    }

    public void setTieBreaker(int tieBreaker) {
        this.tieBreaker = tieBreaker;
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
        if (target != null && target.isPresent()) {
            target.takeDamage((getDamage()));
        }
    }

    @Override
    public final boolean isPresent() {
        return health > 0;
    }

    public final boolean isDead() {
        return !isPresent();
    }

    public final void kill() {
        health = 0;
    }

    public final void act(Simulation grid) {
        if (!isPresent()) return;
        for (Behavior b : getBehaviors()) {
            Action result = b.execute(this, grid);
            if (result == Action.TRANSFORM || result == Action.DEAD) break;
        }
    }

    protected List<Behavior> getBehaviors() {
        return new ArrayList<>();
    }
}
