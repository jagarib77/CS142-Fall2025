package model.items;

import model.entities.LivingEntity;
import util.config.SimulationConstants;

/**
 * Weapon — increases damage, slows movement.
 */
public class Weapon extends Equipment {
    @Override
    protected void applyEffect(LivingEntity target) {
        target.addDamageBonus(SimulationConstants.WEAPON_DAMAGE_BONUS);
        target.addSpeedBonus(SimulationConstants.WEAPON_SPEED_PENALTY);
    }

    @Override
    public char getSymbol() {
        return SimulationConstants.WEAPON_CHAR;
    }
}