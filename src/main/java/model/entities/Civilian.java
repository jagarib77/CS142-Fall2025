package model.entities;

import model.entities.behavior.Action;
import model.entities.behavior.Behavior;
import util.config.SimulationConstants;
import model.world.Simulation;

import java.util.Arrays;
import java.util.List;

public class Civilian extends Human {
    public Civilian() {
        super(SimulationConstants.CIVILIAN_CHAR);
        maxHealth = SimulationConstants.CIVILIAN_HEALTH;
        health = SimulationConstants.CIVILIAN_HEALTH;
        baseDamage = SimulationConstants.CIVILIAN_DAMAGE;
        baseSpeed = SimulationConstants.CIVILIAN_SPEED;
    }


    @Override
    protected List<Behavior> getBehaviors() {
        return Arrays.asList(
                this::pickup,
                this::formSettlement,
                this::infectionCheck
        );
    }

    private Action pickup(LivingEntity me, Simulation g) {
        tryPickup(g);
        return Action.PICKUP;
    }

    private Action formSettlement(LivingEntity me, Simulation g) {
        moveToFormSettlement(g);
        return Action.GROUP;
    }

    private Action infectionCheck(LivingEntity me, Simulation g) {
        decrementInfectionTimer();
        return Action.IDLE;
    }
}
