package model.entities;

import model.entities.behavior.Action;
import model.entities.behavior.Behavior;
import model.world.Simulation;

import java.util.Arrays;
import java.util.List;

import static util.config.SimulationConstants.*;

/**
 * Follows its Elite leader. Repels rival hordes. Never merges.
 */
public class CommonZombie extends Zombie {
    public CommonZombie() {
        maxHealth = health = COMMON_ZOMBIE_HEALTH;
        baseDamage = COMMON_ZOMBIE_DAMAGE;
        baseSpeed = COMMON_ZOMBIE_SPEED;
    }

    @Override
    public char getSymbol() {
        return COMMON_ZOMBIE_CHAR;
    }

    @Override
    protected List<Behavior> getBehaviors() {
        return Arrays.asList(
                this::repelRivals,
                this::followLeader,
                this::hunt,
                this::infect
        );
    }

    private Action repelRivals(LivingEntity me, Simulation g) {
        EliteZombie myLeader = g.findNearestElite(me.getX(), me.getY());
        EliteZombie rival = g.findNearest(me.getX(), me.getY(), EliteZombie.class);
        if (rival != null && rival != myLeader && g.distanceBetween(me, rival) <= RIVAL_REPEL_RANGE) {
            int dx = me.getX() - rival.getX();
            int dy = me.getY() - rival.getY();
            g.moveToward(me.getX() + Integer.signum(dx), me.getY() + Integer.signum(dy), me);
        }
        return Action.MOVE;
    }

    private Action followLeader(LivingEntity me, Simulation g) {
        EliteZombie leader = g.findNearestElite(me.getX(), me.getY());
        if (leader != null && g.distanceBetween(me, leader) > HORDE_FOLLOW_RANGE) {
            g.moveToward(leader.getX(), leader.getY(), me);
        }
        return Action.MOVE;
    }

    private Action hunt(LivingEntity me, Simulation g) {
        g.moveTowardNearest(me, Human.class);
        return Action.MOVE;
    }

    private Action infect(LivingEntity me, Simulation g) {
        infectNearby(g);
        return Action.INFECT;
    }

    private Action attackOrInfect(LivingEntity me, Simulation g) {
        Human target = g.findNearest(me.getX(), me.getY(), Human.class);
        if (target != null) {
            int originalHealth = target.getHealth();
            me.attack(target);
            if (target.getHealth() < originalHealth) {
                boolean infected = infectNearby(g);
                if (infected) return Action.INFECT;
            }
            return Action.ATTACK;
        }
        return Action.IDLE;
    }
}
