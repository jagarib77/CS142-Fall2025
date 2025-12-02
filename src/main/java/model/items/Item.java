package model.items;

import model.entities.LivingEntity;
import model.entities.NonLivingEntity;
import model.entities.behavior.Usable;
import model.world.Cell;
import model.world.Simulation;
import util.DebugLogger;

/**
 * Abstract base class for all usable equipment.
 * When used, applies effect and removes itself from the world.
 */
public abstract class Item extends NonLivingEntity implements Usable {
    protected Item(Cell cell, String displayName) {
        super(cell, displayName);
    }

    /**
     * Uses this equipment on a target entity.
     * Applies the effect and permanently removes the item from the simulation.
     */
    public void useOn(LivingEntity target, Simulation sim) {
        DebugLogger.pickup(target + " equipped " + this);
        applyEffect(target);
        sim.remove(this);
    }

    /** Applies the equipment's effect to the target */
    protected abstract void applyEffect(LivingEntity target);
}