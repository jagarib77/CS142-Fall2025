package model.enums;

/**
 * Move result system.
 * For: Human, Zombie, items, etc.
 */
public enum MoveResult {

    /**
     * Entity successfully moved to the target cell
     */
    SUCCESS,

    /**
     * Target cell is outside the world bounds
     */
    OUT_OF_BOUNDS,

    /**
     * Target cell contains another living entity (human/zombie)
     */
    BLOCKED_BY_LIVING,

    /**
     * Target cell contains an item (weapon, armor, medkit)
     */
    LAND_ON_ITEM,

    /**
     * Move failed because entity is dead, stunned, or invalid
     */
    ENTITY_CANNOT_MOVE,

    /**
     * Target cell is null or invalid
     */
    INVALID_TARGET,

    /**
     * Terrain that can be attacked/destroyed (wall, barricade, door)
     */
    BLOCKED_BY_DESTRUCTIBLE,

    /**
     * Terrain that can never be crossed (mountain, deep water, lava)
     */
    BLOCKED_BY_IMPASSABLE,

    /**
     * Special: pushing another entity (for future mechanics)
     */
    PUSHED_ENTITY,

    /**
     * Special: swapped position with another entity
     */
    SWAPPED_WITH_ENTITY;

    public boolean isSuccess() {
        return this == SUCCESS;
    }

    public boolean isBlocked() {
        return this == BLOCKED_BY_IMPASSABLE;
    }

    public boolean isItemRelated() {
        return this == LAND_ON_ITEM;
    }

    public boolean isCombatPossible() {
        return this == BLOCKED_BY_LIVING || this == BLOCKED_BY_DESTRUCTIBLE;
    }

    public boolean isFatalError() {
        return this == ENTITY_CANNOT_MOVE || this == INVALID_TARGET;
    }
}