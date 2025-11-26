package model.items;

import model.entities.LivingEntity;
import util.config.SimulationConstants;

/**
 * Medkit — heals the user.
 */
public class Medkit extends Equipment {
    @Override
    protected void applyEffect(LivingEntity target) {
        target.takeDamage(-SimulationConstants.MEDKIT_HEAL_AMOUNT);
    }

    @Override
    public char getSymbol() {
        return SimulationConstants.MEDKIT_CHAR;
    }
}