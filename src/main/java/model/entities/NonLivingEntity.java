package model.entities;

/**
 * Base class for non-living objects (items).
 */
public abstract class NonLivingEntity extends Entity {
    protected NonLivingEntity() {
        super(null);
    }

    public boolean isPresent() {
        return true;
    }
}
