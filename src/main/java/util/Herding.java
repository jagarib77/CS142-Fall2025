package util;

import model.entities.LivingEntity;
import model.entities.behavior.Behavior;
import model.enums.Action;
import model.world.Simulation;

import static util.DebugLogger.*;

/**
 * Creates herding/grouping behaviors.
 * Used by Humans and Undead to form settlement.
 */
public class Herding {

    private Herding() {
    }

    /**
     * Makes an entity move toward the nearest member of a group.
     * Stops when within range.
     *
     * @param targetType  e.g. Human.class or Undead.class
     * @param groupName   for debug: "settlement", "horde"
     * @param maxDistance stop moving when this close
     * @return reusable herding behavior
     */
    public static Behavior toward(
            Class<? extends LivingEntity> targetType,
            String groupName,
            int maxDistance) {

        return new Behavior() {
            LivingEntity target;

            @Override
            public Action execute(LivingEntity me, Simulation sim) {
                if (DebugLogger.isEnabled()) {
                    debug("[FOLLOW] Before: " + me + " finding " + groupName);
                }

                target = sim.findNearest(me.getCell(), targetType);

                if (target != null) {
                    int dist = me.getCell().distanceTo(target.getCell());

                    if (dist > maxDistance) {
                        sim.moveToward(target.getCell(), me);
                        if (DebugLogger.isEnabled()) {
                            debug("[FOLLOW] After: " + me + " moves toward " + target);
                        }

                        return Action.MOVE;
                    }
                }

                if (DebugLogger.isEnabled()) {
                    debug("[FOLLOW] After: " + me + " moves toward no one");
                }
                return Action.IDLE;
            }

            @Override
            public String getDebugInfo(LivingEntity me) {
                String msg = "[FOLLOW] " + me + " moves toward ";
                return msg + (target != null ? target : "no one") + " herding (" + groupName + ")";
            }
        };
    }
}
