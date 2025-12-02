package model.entities;


import model.world.Cell;

import static util.config.SimulationConstants.*;

public class EliteZombie extends Undead {
    public EliteZombie(Cell cell) {
        super(cell, ELITE_ZOMBIE_STRING, ELITE_ZOMBIE_HEALTH, ELITE_ZOMBIE_DAMAGE, UNDEAD_DEFENSE_DEFAULT, ELITE_ZOMBIE_SPEED);
    }

    public EliteZombie(Cell cell, String displayName, int health, int damage, int defense, int speed) {
        super(cell, displayName, health, damage, defense, speed);
    }
}