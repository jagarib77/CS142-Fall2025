package model.entities;

import model.entities.behavior.Action;
import model.entities.behavior.Behavior;
import model.world.Simulation;

import java.util.Arrays;
import java.util.List;

import static util.config.SimulationConstants.*;

/**
 * Soldier that defends human settlements.
 */
public class Soldier extends Human {
    public Soldier() {
        super(SOLDIER_CHAR);
        maxHealth = SOLDIER_HEALTH;
        health = SOLDIER_HEALTH;
        baseDamage = SOLDIER_DAMAGE;
        baseSpeed = SOLDIER_SPEED;
    }

    @Override
    protected List<Behavior> getBehaviors() {
        return Arrays.asList(
                this::pickup,
                this::defendSettlement,
                this::attackZombies,
                this::infectionCheck
        );
    }

    private Action pickup(LivingEntity me, Simulation g) {
        tryPickup(g);
        return Action.PICKUP;
    }

    private Action defendSettlement(LivingEntity me, Simulation g) {
        Civilian c = g.findNearest(me.getX(), me.getY(), Civilian.class);
        if (c != null && c.isInSettlement(g) && g.distanceBetween(me, c) > 4) {
            g.moveToward(c.getX(), c.getY(), me);
        }
        return Action.GROUP;
    }

    private Action attackZombies(LivingEntity me, Simulation g) {
        LivingEntity z = g.findNearest(me.getX(), me.getY(), Zombie.class);
        if (z != null && g.distanceBetween(me, z) <= 3) attack(z);
        return Action.ATTACK;
    }

    private Action infectionCheck(LivingEntity me, Simulation g) {
        decrementInfectionTimer();
        return Action.IDLE;
    }
}
