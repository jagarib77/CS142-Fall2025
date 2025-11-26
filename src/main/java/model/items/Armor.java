package model.items;

import model.LivingEntity;
import util.config.SimulationConstants;

/**
 * Armor — increases defense.
 */
public class Armor extends Equipment {
    @Override
    protected void applyEffect(LivingEntity target) {
        target.addDefenseBonus(SimulationConstants.ARMOR_DEFENSE_BONUS);
    }

    @Override
    public char getSymbol() {
        return SimulationConstants.ARMOR_CHAR;
    }
}