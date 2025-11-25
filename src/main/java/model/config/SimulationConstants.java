package model.config;

import java.awt.*;

/**
 * Constants for Zombie Apocalypse Simulation
 */
public final class SimulationConstants {
    private SimulationConstants() {} // Prevent instantiation

    // Grid
    public static final int CELL_SIZE = 12;

    // Status Panel
    public static final int STATUS_PANEL_WIDTH = 170;

    // Font
    public static final Font GAME_OVER_FONT = new Font("Arial", Font.BOLD, 50);
    public static final Font GAME_STAT_FONT = new Font("Arial", Font.PLAIN, 14);

    // Colors
    public static final Color COLOR_CIVILIAN = Color.CYAN;
    public static final Color COLOR_INFECTION = Color.PINK;
    public static final Color COLOR_SOLDIER = Color.BLUE;
    public static final Color COLOR_COMMON_ZOMBIE = Color.YELLOW;
    public static final Color COLOR_ELITE_ZOMBIE = Color.MAGENTA;
    public static final Color COLOR_WEAPON = Color.ORANGE;
    public static final Color COLOR_ARMOR = Color.LIGHT_GRAY;
    public static final Color COLOR_MEDKIT = Color.GREEN;
    public static final Color COLOR_DEFAULT = Color.DARK_GRAY;

    // Overlay
    public static final Color OVERLAY_COLOR = new Color(0, 0, 0, 200);

    // String drawing offsets
    public static final int STRING_X_OFFSET = 3;
    public static final int STRING_Y_OFFSET = 10;

    // Game over text position
    public static final int GAME_OVER_X = 150;
    public static final int GAME_OVER_Y = 200;

    // World
    public static final int WORLD_WIDTH = 70;
    public static final int WORLD_HEIGHT = 40;

    // Search
    public static final int SEARCH_RADIUS_DEFAULT = 1;

    // Settlement System
    public static final int SETTLEMENT_MIN_SIZE = 6;
    public static final int SETTLEMENT_FORMATION_RADIUS = 2;

    // Zombie Horde System
    public static final int HORDE_FOLLOW_RANGE = 6;
    public static final int RIVAL_REPEL_RANGE = 8;

    // Infection
    public static final double INFECTION_RATE_DEFAULT = 0.4;
    public static final int INFECTION_TURNS = 3;
    public static final char INFECTION_CHAR = 'I';
    public static final String INFECTION_STRING = "Infection";

    // Empty
    public static final char EMPTY_CHAR = '.';
    public static final String EMPTY_STRING = "Empty";

    // Equipment
    public static final int WEAPON_DAMAGE_BONUS = 20;
    public static final int WEAPON_SPEED_PENALTY = -2;
    public static final char WEAPON_CHAR = 'W';
    public static final String WEAPON_STRING = "Weapon";

    public static final int ARMOR_DEFENSE_BONUS = 15;
    public static final char ARMOR_CHAR = 'A';
    public static final String ARMOR_STRING = "Armor";

    public static final int MEDKIT_HEAL_AMOUNT = 40;
    public static final char MEDKIT_CHAR = 'M';
    public static final String MEDKIT_STRING = "Medkit";

    // Entity Stats
    public static final int CIVILIAN_HEALTH = 50;
    public static final int CIVILIAN_DAMAGE = 5;
    public static final int CIVILIAN_SPEED = 9;
    public static final char CIVILIAN_CHAR = 'C';
    public static final String CIVILIAN_STRING = "Civilian";

    public static final int SOLDIER_HEALTH = 100;
    public static final int SOLDIER_DAMAGE = 25;
    public static final int SOLDIER_SPEED = 11;
    public static final char SOLDIER_CHAR = 'S';
    public static final String SOLDIER_STRING = "Soldier";

    public static final int COMMON_ZOMBIE_HEALTH = 80;
    public static final int COMMON_ZOMBIE_DAMAGE = 15;
    public static final int COMMON_ZOMBIE_SPEED = 10;
    public static final char COMMON_ZOMBIE_CHAR = 'Z';
    public static final String COMMON_ZOMBIE_STRING = "Common Zombie";

    public static final int ELITE_ZOMBIE_HEALTH = 150;
    public static final int ELITE_ZOMBIE_DAMAGE = 35;
    public static final int ELITE_ZOMBIE_SPEED = 14;
    public static final int ELITE_ZOMBIE_HORDE_DAMAGE_BONUS_PER_FOLLOWER = 4;
    public static final char ELITE_ZOMBIE_CHAR = 'E';
    public static final String ELITE_ZOMBIE_STRING = "Elite Zombie";

    // Spawn Counts
    public static final int SPAWN_CIVILIANS = 20;
    public static final int SPAWN_SOLDIERS = 8;
    public static final int SPAWN_COMMON_ZOMBIES = 12;
    public static final int SPAWN_ELITES = 3;
    public static final int SPAWN_WEAPONS = 10;
    public static final int SPAWN_ARMORS = 6;
    public static final int SPAWN_MEDKITS = 8;
}