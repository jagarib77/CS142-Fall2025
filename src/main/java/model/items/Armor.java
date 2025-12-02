package model.items;

import model.entities.LivingEntity;
import model.world.Cell;
import util.DebugLogger;

import static util.config.SimulationConstants.*;

/**
 * Armor — increases defense.
 */
public class Armor extends Item {
    private final int defenseBonus;
    private final int speedPenalty;

    public Armor(Cell cell) {
        this(cell, ARMOR_STRING, ARMOR_DEFENSE_BONUS, ARMOR_SPEED_PENALTY);
    }

    public Armor(Cell cell, String name, int defBonus, int speedPen) {
        super(cell, name);
        this.defenseBonus = defBonus;
        this.speedPenalty = speedPen;
    }

    @Override
    protected void applyEffect(LivingEntity target) {
        int oldDef = target.getDefense();
        int oldSpd = target.getSpeed();

        target.addDefenseBonus(defenseBonus);
        target.addSpeedBonus(speedPenalty);

        DebugLogger.pickup(target + " used" + this +
                " -> DEF " + oldDef + " to " + target.getDefense() +
                ", SPD " + oldSpd + " to " + target.getSpeed());
    }

    @Override
    public String toString() {
        String base = super.toString();
        String bonus = "";

        if (defenseBonus > 0) {
            bonus += " DEF+" + defenseBonus;
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