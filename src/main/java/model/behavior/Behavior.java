package model.behavior;

import model.LivingEntity;
import model.world.SimulationGrid;

/**
 * Functional interface for entity behaviors.
 */
@FunctionalInterface
public interface Behavior {
    Action execute(LivingEntity entity, SimulationGrid grid);
}