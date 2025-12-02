package model.entities.behavior;

import model.entities.LivingEntity;
import model.world.Simulation;

/**
 * Anything that can be picked up and used by a LivingEntity.
 */
public interface Usable {
    void useOn(LivingEntity target, Simulation sim);
}
