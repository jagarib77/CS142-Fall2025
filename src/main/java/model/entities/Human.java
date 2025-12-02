package model.entities;

import model.entities.behavior.Behavior;
import model.entities.behavior.Usable;
import model.enums.Action;
import model.enums.Faction;
import model.items.Item;
import model.world.Cell;
import model.world.Simulation;
import util.DebugLogger;
import util.Herding;

import java.util.ArrayList;
import java.util.List;

import static util.DebugLogger.*;
import static util.config.SimulationConstants.*;

/**
 * Base class for humans with pickup, infectionProgress and formSettlement behavior.
 */
public abstract class Human extends LivingEntity {
    protected boolean infected = false;
    protected int infectionTurns = 0;

    protected Human(Cell cell) {
        this(cell, HUMAN_STRING_DEFAULT,
                HUMAN_HEALTH_DEFAULT,
                HUMAN_DAMAGE_DEFAULT,
                HUMAN_DEFENSE_DEFAULT,
                HUMAN_SPEED_DEFAULT);
    }

    protected Human(Cell cell, String displayName,
                    int maxHealth, int damage, int defense, int speed) {
        super(cell, displayName, maxHealth, damage, defense, speed, Faction.HUMAN);
    }

    @Override
    protected List<Behavior> getBehaviors() {
        List<Behavior> behaviors = new ArrayList<>();
        behaviors.add(pickup);
        behaviors.add(formSettlement);
        behaviors.add(infectionProgress);
        return behaviors;
    }

    private final Behavior pickup = new Behavior() {
        Usable target;

        @Override
        public Action execute(LivingEntity me, Simulation sim) {
            if (isEnabled()) {
                pickup("Before: " + me + " scanning for item");
            }

            if (me instanceof Human) {
                for (int dy = -SEARCH_RADIUS_DEFAULT; dy <= SEARCH_RADIUS_DEFAULT; dy++) {
                    for (int dx = -SEARCH_RADIUS_DEFAULT; dx <= SEARCH_RADIUS_DEFAULT; dx++) {
                        int x = me.getX() + dx;
                        int y = me.getY() + dy;

                        if (!sim.isValid(x, y)) continue;

                        Entity e = sim.get(new Cell(x, y));
                        if (e instanceof Item) {
                            target = (Item) e;
                            target.useOn((Human) me, sim);
                            if (isEnabled()) {
                                pickup("After: " + me + " uses " + target);
                            }
                            return Action.PICKUP;
                        }
                    }
                }
            }

            if (isEnabled()) {
                pickup("After: " + me + " picks up no item");
            }
            return Action.IDLE;
        }

        @Override
        public String getDebugInfo(LivingEntity me) {
            String msg = "[PICK UP] " + me + " picks up ";
            return msg + (target != null ? target : "no item");
        }
    };

    private final Behavior formSettlement =
            Herding.toward(Human.class, "settlement", SETTLEMENT_FORMATION_RADIUS);

    private final Behavior infectionProgress = new Behavior() {
        @Override
        public Action execute(LivingEntity me, Simulation sim) {
            if (infected) {
                if (isEnabled()) {
                    debug("[INFECT] Before: " + me + " infection timer: " + infectionTurns);
                }

                decrementInfectionTurns();

                if (infectionTurns == 0) {
                    if (isEnabled()) {
                        debug("[TRANSFORM] After: " + me + " transform to zombie");
                    }

                    return Action.TRANSFORM;
                }
                if (isEnabled()) {
                    debug("[INFECT] After: " + me + " infection timer: " + infectionTurns);
                }
                return Action.INFECT;
            }
            if (isEnabled()) {
                debug("[INFECT] After: " + me + " healthy");
            }
            return Action.IDLE;
        }

        @Override
        public String getDebugInfo(LivingEntity me) {
            if (!infected) return "healthy";
            return "INFECTED (" + infectionTurns + " turns)";
        }
    };

    @Override
    public char getSymbol() {
        if (shouldBecomeZombie()) return COMMON_ZOMBIE_CHAR;
        if (infectionTurns > 0) return INFECTION_CHAR;
        return super.getSymbol();
    }

    public boolean isInfected() {
        return infected;
    }

    public int getInfectionTurns() {
        return this.infectionTurns;
    }

    private void setInfectionTurns(int turns) {
        this.infectionTurns = turns;
    }

    public void infect(int turns) {
        if (!infected) {
            infected = true;
            setInfectionTurns(turns);
        }
    }

    protected void decrementInfectionTurns() {
        if (infected) setInfectionTurns(Math.max(0, infectionTurns - 1));
    }

    public boolean shouldBecomeZombie() {
        return infected && infectionTurns <= 0;
    }

    @Override
    public String toString() {
        String base = super.toString();
        if (infected) {
            base += " [INFECTED";
            if (infectionTurns > 0) base += " " + infectionTurns + "t";
            base += "]";
        }
        return base;
    }
}
