package model.entities;

import model.entities.behavior.Behavior;
import model.enums.Action;
import model.enums.Faction;
import model.items.Item;
import model.world.Cell;
import model.world.Simulation;
import util.DebugLogger;
import util.Herding;

import java.util.ArrayList;
import java.util.List;

import static util.DebugLogger.debug;
import static util.config.SimulationConstants.*;

/**
 * Base class for zombies with infection spread.
 */
public abstract class Undead extends LivingEntity {
    protected double infectionRate = INFECTION_RATE_DEFAULT;
    protected int infectionTurns = INFECTION_TURNS;

    protected Undead(Cell cell, String displayName,
                     int maxHealth, int damage, int defense, int speed) {
        super(cell, UNDEAD_STRING_DEFAULT, UNDEAD_HEALTH_DEFAULT, UNDEAD_DAMAGE_DEFAULT, UNDEAD_DEFENSE_DEFAULT, UNDEAD_SPEED_DEFAULT, Faction.UNDEAD);
    }

    public double getInfectionRate() {
        return infectionRate;
    }

    public int getInfectionTurns() {
        return infectionTurns;
    }

    @Override
    protected List<Behavior> getBehaviors() {
        List<Behavior> behaviors = new ArrayList<>();
        behaviors.add(attackHuman);
        behaviors.add(huntHuman);
        behaviors.add(avoidItem);
        behaviors.add(formHorde);
        return behaviors;
    }

    private final Behavior avoidItem = new Behavior() {
        Item target;

        @Override
        public Action execute(LivingEntity me, Simulation sim) {
            if (DebugLogger.isEnabled()) {
                debug("[MOVE] Before: " + me + " scanning for item");
            }

            for (int dy = -SEARCH_RADIUS_DEFAULT; dy <= SEARCH_RADIUS_DEFAULT; dy++) {
                for (int dx = -SEARCH_RADIUS_DEFAULT; dx <= SEARCH_RADIUS_DEFAULT; dx++) {
                    int x = me.getX() + dx;
                    int y = me.getY() + dy;
                    if (!sim.isValid(x, y)) continue;

                    Entity e = sim.get(new Cell(x, y));
                    if (e instanceof Item) {
                        target = (Item) e;
                        sim.moveAwayFrom(me, target);
                        if (DebugLogger.isEnabled()) {
                            debug("[MOVE] After: " + me + " avoids item: " + target);
                        }
                        return Action.MOVE;
                    }
                }
            }
            if (DebugLogger.isEnabled()) {
                debug("[MOVE] After: " + me + " avoids no item");
            }
            return Action.IDLE;
        }

        @Override
        public String getDebugInfo(LivingEntity me) {
            String msg = "[MOVE] " + me + " picks up ";
            return msg + (target != null ? target : "no item");
        }
    };

    private final Behavior huntHuman = new Behavior() {
        Human target;

        @Override
        public Action execute(LivingEntity me, Simulation sim) {
            if (DebugLogger.isEnabled()) {
                debug("[HUNT] Before: " + me + " hunting humans");
            }

            target = sim.findNearest(me.getCell(), Human.class);
            if (target != null) {
                sim.moveToward(target.getCell(), me);
                if (DebugLogger.isEnabled()) {
                    debug("[HUNT] After: " + me + " hunts " + target);
                }
                return Action.MOVE;
            }

            if (DebugLogger.isEnabled()) {
                debug("[HUNT] After: " + me + " hunts no human");
            }
            return Action.IDLE;
        }

        @Override
        public String getDebugInfo(LivingEntity me) {
            String msg = "[HUNT] After: " + me + " moved toward ";
            return msg + (target != null ? target : "no human");
        }
    };

    private final Behavior attackHuman = new Behavior() {
        Human target;

        @Override
        public Action execute(LivingEntity me, Simulation sim) {
            if (DebugLogger.isEnabled()) {
                debug("[ATTACK] Before: " + me + " checking attack range");
            }

            target = sim.findNearest(me.getCell(), Human.class);
            if (target != null) {
                me.attack(target);

                if (DebugLogger.isEnabled()) {
                    DebugLogger.combat("After: " + me + " attacks " + target);
                }
                return Action.ATTACK;
            }
            if (DebugLogger.isEnabled()) {
                DebugLogger.combat("After: " + me + " finds no human to attack");
            }
            return Action.IDLE;
        }

        @Override
        public String getDebugInfo(LivingEntity me) {
            String msg = "[ATTACK] After: " + me + " attacks ";
            return msg + (target != null ? target : " no human");
        }
    };

    private final Behavior formHorde =
            Herding.toward(Undead.class, "horde", HORDE_FOLLOW_RANGE);
}