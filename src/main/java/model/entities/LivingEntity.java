package model.entities;

import model.entities.behavior.Behavior;
import model.entities.behavior.Usable;
import model.enums.Action;
import model.enums.Faction;
import model.world.Cell;
import model.world.Simulation;
import util.DebugLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static util.DebugLogger.*;
import static util.config.SimulationConstants.MIN_DAMAGE_DEFAULT;

/**
 * Base class for all living entities (humans, zombies).
 * Contains health, stats, faction, initiative, and behavior system.
 */
public abstract class LivingEntity extends Entity {

    private static final Random RANDOM = new Random();

    protected int maxHealth;
    protected int health;
    protected int baseAttack;
    protected int baseDefense;
    protected int baseSpeed;

    private int attackBonus = 0;
    private int defenseBonus = 0;
    private int speedBonus = 0;

    private final int tieBreaker;

    private final Faction faction;

    protected LivingEntity(Cell cell, String displayName, int maxHealth, int attack, int defense, int speed, Faction faction) {
        super(cell, displayName);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.baseAttack = attack;
        this.baseDefense = defense;
        this.baseSpeed = speed;
        this.faction = Objects.requireNonNull(faction, "Faction cannot be null");
        this.tieBreaker = RANDOM.nextInt(100);
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getAttack() {
        return baseAttack + attackBonus;
    }

    public int getDefense() {
        return baseDefense + defenseBonus;
    }

    public int getSpeed() {
        return Math.max(1, baseSpeed + speedBonus);
    }

    public void addAttackBonus(int bonus) {
        this.attackBonus += bonus;
    }

    public void addDefenseBonus(int bonus) {
        this.defenseBonus += bonus;
    }

    public void addSpeedBonus(int bonus) {
        this.speedBonus += bonus;
    }

    public int getInitiative() {
        return getSpeed() * 100 + tieBreaker;
    }

    public int getTieBreaker() {
        return tieBreaker;
    }

    public boolean takeDamage(int rawDamage, LivingEntity attacker) {
        if (!isPresent()) return false;

        if (rawDamage <= 0) {
            health = Math.min(maxHealth, health - rawDamage);
            combat(this + " has been HEALED by " + attacker);
            return false;
        }

        int damageTaken = Math.max(MIN_DAMAGE_DEFAULT, rawDamage - getDefense());
        health = Math.max(0, health - damageTaken);

        boolean wasHit = damageTaken > MIN_DAMAGE_DEFAULT;
        boolean died = health == 0;

        if (died) {
            combat(this + " has been KILLED by " + attacker);
        } else if (wasHit) {
            combat(this + " takes " + damageTaken + " damage from " + attacker);

            if (this instanceof Human && attacker instanceof Undead) {
                Undead zombie = (Undead) attacker;
                if (RANDOM.nextDouble() < zombie.getInfectionRate()) {
                    ((Human) this).infect(zombie.getInfectionTurns());
                    combat(this + " infected by " + attacker);
                }
            }
        }

        return wasHit;
    }

    public void attack(LivingEntity target) {
        if (target != null && target.isPresent()) {
            int damage = getAttack();
            combat(this + " attacks " + target + " for " + damage + " dmg");
            target.takeDamage(damage, this);
        }
    }

    public void heal(int amount) {
        if (amount > 0) {
            this.health = Math.min(this.maxHealth, this.health + amount);
        }
    }

    @Override
    public final boolean isPresent() {
        return health > 0;
    }

    public final boolean isDead() {
        return health <= 0;
    }

    public final void kill() {
        health = 0;
    }

    public Faction getFaction() {
        return faction;
    }

    public boolean isEnemyOf(LivingEntity other) {
        Objects.requireNonNull(other, "Cannot check enmity with null entity");

        if (this.faction == Faction.NEUTRAL || other.faction == Faction.NEUTRAL) {
            return false;
        }
        return this.faction != other.faction;
    }

    public boolean isAllyOf(LivingEntity other) {
        Objects.requireNonNull(other, "Cannot check enmity with null entity");

        if (this.faction == Faction.NEUTRAL || other.faction == Faction.NEUTRAL) {
            return false;
        }
        return this.faction == other.faction;
    }

    public final void act(Simulation simulation) {
        if (!isPresent()) return;

        if (isEnabled()) {
            System.out.println();
            System.out.println();
            debug("=== " + this + " acting (initiative: " + this.getInitiative() + ") ===");
        }

        for (Behavior behavior : getBehaviors()) {
            Action action = behavior.execute(this, simulation);

            if (isEnabled()) {
                String debugInfo = behavior.getDebugInfo(this);
                if (debugInfo != null && !debugInfo.trim().isEmpty()) {
                    info(this + " → " + debugInfo);
                }
            }

            if (action == Action.DEAD || action == Action.TRANSFORM) {
                if (isEnabled()) {
                    combat(this + " stopped acting: " + action);
                }
                break;
            }
        }
    }

    protected List<Behavior> getBehaviors() {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        String base = super.toString();

        if (!isPresent()) {
            return base + " [DEAD]";
        }

        String state = String.format(" atk=%d, def=%d, hp=%d/%d, spd=%d",
                getAttack(), getDefense(), getHealth(), getMaxHealth(), getSpeed());

        return base + state;
    }
}