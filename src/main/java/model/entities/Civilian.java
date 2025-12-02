package model.entities;

import model.entities.behavior.Behavior;
import model.enums.Action;
import model.world.Cell;
import model.world.Simulation;
import util.DebugLogger;

import java.util.ArrayList;
import java.util.List;

import static util.DebugLogger.*;
import static util.config.SimulationConstants.*;

public class Civilian extends Human {
    public Civilian(Cell cell) {
        super(cell, CIVILIAN_STRING, CIVILIAN_HEALTH, CIVILIAN_DAMAGE, HUMAN_DEFENSE_DEFAULT, CIVILIAN_SPEED);
    }

    public Civilian(Cell cell, String name, int maxHeath, int damage, int defense, int speed) {
        super(cell, name, maxHeath, damage, defense, speed);
    }

    @Override
    protected List<Behavior> getBehaviors() {
        List<Behavior> behaviors = new ArrayList<>(super.getBehaviors());
        behaviors.add(0, runAway);
        return behaviors;
    }

    private final Behavior runAway = new Behavior() {
        Undead zombie;

        @Override
        public Action execute(LivingEntity me, Simulation sim) {
            if (isEnabled()) {
                debug("[MOVE] Before: " + me + " scanning zombies");
            }

            zombie = sim.findNearest(me.getCell(), Undead.class);

            if (zombie != null && me.getCell().distanceTo(zombie.getCell()) <= ZOMBIE_REPEL_RANGE) {
                sim.moveAwayFrom(me, zombie);
                if (isEnabled()) {
                    debug("[MOVE] After: " + me + " moved away from " + zombie);
                }

                return Action.MOVE;
            }

            if (isEnabled()) {
                debug("[MOVE] After: " + me + " moved away from no one");
            }
            return Action.IDLE;
        }

        @Override
        public String getDebugInfo(LivingEntity me) {
            String msg = "[MOVE] " + me + " moved away from ";
            return msg + (zombie != null ? zombie : "no zombie");
        }
    };
}
