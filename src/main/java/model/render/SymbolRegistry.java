package model.render;

import model.entities.*;
import model.items.Armor;
import model.items.Medkit;
import model.items.Weapon;

import java.util.HashMap;
import java.util.Map;

import static util.config.SimulationConstants.*;

/**
 * Central registry for all entity symbols.
 */
public final class SymbolRegistry {

    private static final Map<Class<? extends Entity>, Character> SYMBOLS = new HashMap<>();

    static {
        // === TERRAIN ===
//        register(Floor.class,       '.');
//        register(Grass.class,       ',');
//        register(Water.class,       '~');
//        register(Wall.class,         '#');

        // === HUMANS ===
        register(Civilian.class,    CIVILIAN_CHAR);
        register(Soldier.class,     SOLDIER_CHAR);
//        register(Medic.class,   'D');

        // === ZOMBIES ===
        register(CommonZombie.class,COMMON_ZOMBIE_CHAR);
        register(EliteZombie.class,  ELITE_ZOMBIE_CHAR);
//        register(TankZombie.class,  'T');

        // === ITEMS ===
//        register(Sword.class,       '/');
//        register(Gun.class,         '!');
        register(Weapon.class,         WEAPON_CHAR);
        register(Medkit.class,      MEDKIT_CHAR);
        register(Armor.class,       ARMOR_CHAR);
    }

    private SymbolRegistry() {}

    public static void register(Class<? extends Entity> entityClass, char symbol) {
        Character existing = SYMBOLS.put(entityClass, symbol);
        if (existing != null) {
            throw new IllegalStateException("Symbol conflict: " + entityClass.getSimpleName() +
                    " tried to use '" + symbol + "' but it was already used by another class");
        }
    }

    public static char getSymbolFor(Entity entity) {
        Character symbol = SYMBOLS.get(entity.getClass());
        if (symbol != null) {
            return symbol;
        }

        String name = entity.getClass().getSimpleName();
        return name.isEmpty() ? '?' : Character.toUpperCase(name.charAt(0));
    }
}