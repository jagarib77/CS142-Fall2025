package model.entities;

import model.LivingEntity;
import model.behavior.Action;
import model.behavior.Behavior;
import static model.config.SimulationConstants.*;
import model.world.SimulationGrid;

import java.util.List;

/**
 * Soldier that defends human settlements.
 */
public class Soldier extends Human {
    public Soldier() {
        super(SOLDIER_CHAR);
        maxHealth =  SOLDIER_HEALTH;
        health = SOLDIER_HEALTH;
        baseDamage = SOLDIER_DAMAGE;
        baseSpeed = SOLDIER_SPEED;
    }

    @Override protected List<Behavior> getBehaviors() {
        return List.of(
                this::pickup,
                this::defendSettlement,
                this::attackZombies,
                this::infectionCheck
        );
    }

    private Action pickup(LivingEntity me, SimulationGrid g) { tryPickup(g); return Action.PICKUP; }
    private Action defendSettlement(LivingEntity me, SimulationGrid g) {
        Civilian c = g.findNearest(me.getX(), me.getY(), Civilian.class);
        if (c != null && c.isInSettlement(g) && g.distanceBetween(me, c) > 4) {
            g.moveToward(c.getX(), c.getY(), me);
        }
        return Action.GROUP;
    }
    private Action attackZombies(LivingEntity me, SimulationGrid g) {
        var z = g.findNearest(me.getX(), me.getY(), Zombie.class);
        if (z != null && g.distanceBetween(me, z) <= 3) attack(z);
        return Action.ATTACK;
    }
    private Action infectionCheck(LivingEntity me, SimulationGrid g) { decrementInfectionTimer(); return Action.IDLE; }
}
