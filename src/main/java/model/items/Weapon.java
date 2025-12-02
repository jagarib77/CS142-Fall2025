package model.items;

import model.entities.LivingEntity;
import model.world.Cell;
import util.DebugLogger;

import static util.config.SimulationConstants.*;

/**
 * Weapon — increases attack, slows movement.
 */
public class Weapon extends Item {
    private final int attackBonus;
    private final int speedPenalty;

    public Weapon(Cell cell) {
        this(cell, WEAPON_STRING, WEAPON_ATTACK_BONUS, WEAPON_SPEED_PENALTY);
    }

    public Weapon(Cell cell, String name, int dmgBonus, int speedPen) {
        super(cell, name);
        this.attackBonus = dmgBonus;
        this.speedPenalty = speedPen;
    }

    @Override
    protected void applyEffect(LivingEntity target) {
        int oldAtk = target.getAttack();
        int oldSpd = target.getSpeed();

        target.addAttackBonus(attackBonus);
        target.addSpeedBonus(speedPenalty);

        DebugLogger.pickup(target + " used" + this +
                " -> ATK " + oldAtk + " to " + target.getAttack() +
                ", SPD " + oldSpd + " to " + target.getSpeed());
    }

    @Override
    public String toString() {
        String base = super.toString();
        String bonus = "";

        if (attackBonus > 0) {
            bonus += " ATK+" + attackBonus;
        }

        if (speedPenalty > 0) {
            bonus += " SPD-" + speedPenalty;
        }

        if (bonus.isEmpty()) {
            bonus = " (no effect)";
        }

        return base + bonus;
    }
}