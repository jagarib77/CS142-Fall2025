package model.behavior;

import model.LivingEntity;
import model.world.Simulation;

/**
 * Functional interface for entity behaviors.
 */
@FunctionalInterface
public interface Behavior {
    Action execute(LivingEntity entity, Simulation grid);
}