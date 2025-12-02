package model.entities;

import model.entities.behavior.Behavior;
import model.enums.Action;
import model.world.Cell;
import model.world.Simulation;
import util.DebugLogger;

import java.util.ArrayList;
import java.util.List;

import static util.DebugLogger.debug;
import static util.config.SimulationConstants.*;

public class Soldier extends Human {
    public Soldier(Cell cell) {
        super(cell, SOLDIER_STRING, SOLDIER_HEALTH, SOLDIER_DAMAGE, SOLDIER_DEFENSE, SOLDIER_SPEED);
    }

    public Soldier(Cell cell, String name, int maxHeath, int damage, int defense, int speed) {
        super(cell, name, maxHeath, damage, defense, speed);
    }

    @Override
    protected List<Behavior> getBehaviors() {
        List<Behavior> behaviors = new ArrayList<>(super.getBehaviors());
        behaviors.add(1, attackUndead);
        return behaviors;
    }

    private final Behavior attackUndead = new Behavior() {
        Undead target;

        @Override
        public Action execute(LivingEntity me, Simulation sim) {
            if (DebugLogger.isEnabled()) {
                debug("[ATTACK] Before: " + me + " checking attack range");
            }

            target = sim.findNearest(me.getCell(), Undead.class);
            if (target != null) {
                me.attack(target);
                return Action.ATTACK;
            }
            return Action.IDLE;
        }

        @Override
        public String getDebugInfo(LivingEntity me) {
            String msg = "[ATTACK] After: " + me + " attacks ";
            return msg + (target != null ? target : " no zombie");
        }
    };
}
