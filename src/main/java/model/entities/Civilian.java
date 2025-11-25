package model.entities;

import model.LivingEntity;
import model.behavior.Action;
import model.behavior.Behavior;
import model.config.SimulationConstants;
import model.world.SimulationGrid;

import java.util.List;

public class Civilian extends Human{
    public Civilian() {
        super(SimulationConstants.CIVILIAN_CHAR);
        maxHealth = SimulationConstants.CIVILIAN_HEALTH;
        health = SimulationConstants.CIVILIAN_HEALTH;
        baseDamage = SimulationConstants.CIVILIAN_DAMAGE;
        baseSpeed = SimulationConstants.CIVILIAN_SPEED;
    }


    @Override protected List<Behavior> getBehaviors() {
        return List.of(
                this::pickup,
                this::formSettlement,
                this::infectionCheck
        );
    }

    private Action pickup(LivingEntity me, SimulationGrid g) { tryPickup(g); return Action.PICKUP; }
    private Action formSettlement(LivingEntity me, SimulationGrid g) { moveToFormSettlement(g); return Action.GROUP; }
    private Action infectionCheck(LivingEntity me, SimulationGrid g) { decrementInfectionTimer(); return Action.IDLE; }
}
