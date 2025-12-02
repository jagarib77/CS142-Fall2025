package model.entities;

import model.entities.behavior.Behavior;
import model.enums.Action;
import model.world.Cell;
import model.world.Simulation;

import java.util.ArrayList;
import java.util.List;

import static util.DebugLogger.*;
import static util.config.SimulationConstants.*;

/**
 * Follows its Elite leader. Repels rival hordes. Never merges.
 */
public class CommonZombie extends Undead {
    public CommonZombie(Cell cell) {
        super(cell, COMMON_ZOMBIE_STRING, COMMON_ZOMBIE_HEALTH, COMMON_ZOMBIE_DAMAGE, UNDEAD_DEFENSE_DEFAULT, COMMON_ZOMBIE_SPEED);
    }

    public CommonZombie(Cell cell, String displayName, int health, int damage, int defense, int speed) {
        super(cell, displayName, health, damage, defense, speed);
    }

//    @Override
//    protected List<Behavior> getBehaviors() {
//        List<Behavior> behaviors = new ArrayList<>(super.getBehaviors());
//        behaviors.add(2, repelRivals);
//        return behaviors;
//    }
//
//    private final Behavior repelRivals = new Behavior() {
//        EliteZombie rival;
//
//        @Override
//        public Action execute(LivingEntity me, Simulation sim) {
//            debug("[REPEL] Before: " + me + " scanning rivals");
//
//            rival = sim.findNearest(me.getCell(), EliteZombie.class);
//
//            if (rival != null && me.getCell().distanceTo(rival.getCell()) <= RIVAL_REPEL_RANGE) {
//                sim.moveAwayFrom(me, rival);
//                return Action.MOVE;
//            }
//            return Action.IDLE;
//        }
//
//        @Override
//        public String getDebugInfo(LivingEntity me) {
//            String msg = "[REPEL] After: " + me + " moved away from ";
//            return msg + (rival != null ? rival : "no rival");
//        }
//    };
}
