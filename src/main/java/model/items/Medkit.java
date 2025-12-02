package model.items;

import model.entities.LivingEntity;
import model.world.Cell;
import util.DebugLogger;

import static util.config.SimulationConstants.MEDKIT_HEAL_AMOUNT;
import static util.config.SimulationConstants.MEDKIT_STRING;

/**
 * Medkit — heals the user.
 */
public class Medkit extends Item {
    private final int healAmount;

    public Medkit(Cell cell) {
        this(cell, MEDKIT_STRING, MEDKIT_HEAL_AMOUNT);
    }

    protected Medkit(Cell cell, String displayName, int healAmount) {
        super(cell, displayName);
        this.healAmount = healAmount;
    }

    @Override
    protected void applyEffect(LivingEntity target) {
        int oldHp = target.getHealth();
        target.heal(healAmount);
        DebugLogger.pickup(target + " used" + this +
                " -> HP " + oldHp + " to " + target.getHealth());
    }

    @Override
    public String toString() {
        String base = super.toString();
        String bonus = healAmount > 0 ? " HP+" + healAmount : " (no effect)";

        return base + bonus;
    }
}