package model;

public abstract class NonLivingEntity extends Entity {
    @Override
    public final boolean isAlive() {
        return false;
    }
}
