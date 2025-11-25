package model;

import java.util.Random;

public abstract class LivingEntity extends Entity {
    protected int health;
    protected int maxHealth;
    protected int baseDamage = 10;
    protected int baseDefense = 0;
    protected int baseSpeed = 10;
    private int damageBonus = 0;
    private int defenseBonus = 0;
    private int speedBonus = 0;
    protected final Random rng = new Random();

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
        int dmgTaken = Math.max(0, dmg - getDefense());
        health = Math.max(0, health - dmgTaken);
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

    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }
}
