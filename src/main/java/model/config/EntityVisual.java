package model.config;

import java.awt.Color;

import static model.config.SimulationConstants.*;

/**
 * Enum to map entity symbols to their colors and display names.
 * Used by GridPanel and StatusPanel for rendering.
 */
public enum EntityVisual {
    EMPTY(EMPTY_CHAR, COLOR_DEFAULT, EMPTY_STRING),
    INFECTION(INFECTION_CHAR, COLOR_INFECTION, INFECTION_STRING),
    CIVILIAN(CIVILIAN_CHAR, COLOR_CIVILIAN, CIVILIAN_STRING),
    SOLDIER(SOLDIER_CHAR, COLOR_SOLDIER, SOLDIER_STRING),
    COMMON_ZOMBIE(COMMON_ZOMBIE_CHAR, COLOR_COMMON_ZOMBIE, COMMON_ZOMBIE_STRING),
    ELITE_ZOMBIE(ELITE_ZOMBIE_CHAR, COLOR_ELITE_ZOMBIE, ELITE_ZOMBIE_STRING),
    WEAPON(WEAPON_CHAR, COLOR_WEAPON, WEAPON_STRING),
    ARMOR(ARMOR_CHAR, COLOR_ARMOR, ARMOR_STRING),
    MEDKIT(MEDKIT_CHAR, COLOR_MEDKIT, MEDKIT_STRING);

    private final char symbol;
    private final Color color;
    private final String displayName;

    EntityVisual(char symbol, Color color, String displayName) {
        this.symbol = symbol;
        this.color = color;
        this.displayName = displayName;
    }

    public char getSymbol() { return symbol; }
    public Color getColor() { return color; }
    public String getDisplayName() { return displayName; }

    public static EntityVisual fromChar(char c) {
        for (EntityVisual e : values()) {
            if (e.symbol == c) return e;
        }
        return EMPTY;
    }
}
