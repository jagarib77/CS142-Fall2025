package model;

/**
 * Base class for non-living objects (items).
 */
public abstract class NonLivingEntity extends Entity {
    public boolean isPresent() {
        return true;
    }
}
