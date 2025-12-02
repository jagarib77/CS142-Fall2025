package model.entities.behavior;

import model.entities.LivingEntity;
import model.enums.Action;
import model.world.Simulation;

/**
 * Interface for entity behaviors.
 */
public interface Behavior {
    /**
     * @return the type of action performed
     */
    Action execute(LivingEntity me, Simulation sim);

    /**
     * Optional debug description. Default = empty.
     * Override in concrete behaviors for rich logging.
     */
    default String getDebugInfo(LivingEntity me) {
        return "";
    }
}