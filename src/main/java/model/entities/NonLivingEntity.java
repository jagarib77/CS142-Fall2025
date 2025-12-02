package model.entities;

import model.enums.Faction;
import model.world.Cell;

/**
 * Base class for non-living objects (items).
 */
public abstract class NonLivingEntity extends Entity {
    private final Faction faction;

    protected NonLivingEntity(Cell cell, String displayName) {
        super(cell, displayName);
        this.faction = Faction.NEUTRAL;
    }

    public Faction getFaction() {
        return faction;
    }
}
