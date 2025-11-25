package model.config;

/**
 * Single source of truth for ALL game balance values.
 * No magic numbers allowed anywhere in the codebase.
 */
public final class SimulationConstants {



    private SimulationConstants() {} // Prevent instantiation

    // World
    public static final int WORLD_WIDTH = 70;
    public static final int WORLD_HEIGHT = 40;

    // Settlement System
    public static final int SETTLEMENT_MIN_SIZE = 6;
    public static final int SETTLEMENT_DEFENSE_RANGE = 2;

    // Zombie Horde System
    public static final int HORDE_FOLLOW_RANGE = 6;
    public static final int RIVAL_REPEL_RANGE = 8;

    // Infection
    public static final int INFECTION_TURNS = 3;

    // Equipment
    public static final int WEAPON_DAMAGE_BONUS = 20;
    public static final int WEAPON_SPEED_PENALTY = -2;
    public static final char WEAPON_CHAR = 'W';

    public static final int ARMOR_DEFENSE_BONUS = 15;
    public static final char ARMOR_CHAR = 'A';

    public static final int MEDKIT_HEAL_AMOUNT = 40;
    public static final char MEDKIT_CHAR = 'M';

    // Entity Stats
    public static final int CIVILIAN_HEALTH = 50;
    public static final int CIVILIAN_DAMAGE = 5;
    public static final int CIVILIAN_SPEED = 9;
    public static final char CIVILIAN_CHAR = 'C';

    public static final int SOLDIER_HEALTH = 100;
    public static final int SOLDIER_DAMAGE = 25;
    public static final int SOLDIER_SPEED = 11;
    public static final char SOLDIER_CHAR = 'S';

    public static final int COMMON_ZOMBIE_HEALTH = 80;
    public static final int COMMON_ZOMBIE_DAMAGE = 15;
    public static final int COMMON_ZOMBIE_SPEED = 10;
    public static final char COMMON_ZOMBIE_CHAR = 'Z';

    public static final int ELITE_ZOMBIE_HEALTH = 150;
    public static final int ELITE_ZOMBIE_DAMAGE = 35;
    public static final int ELITE_ZOMBIE_SPEED = 14;
    public static final int ELITE_ZOMBIE_HORDE_DAMAGE_BONUS_PER_FOLLOWER = 4;
    public static final char ELITE_ZOMBIE_CHAR = 'E';

    // Spawn Counts
    public static final int SPAWN_CIVILIANS = 20;
    public static final int SPAWN_SOLDIERS = 8;
    public static final int SPAWN_COMMON_ZOMBIES = 12;
    public static final int SPAWN_ELITES = 3;
    public static final int SPAWN_WEAPONS = 10;
    public static final int SPAWN_ARMORS = 6;
    public static final int SPAWN_MEDKITS = 8;
}