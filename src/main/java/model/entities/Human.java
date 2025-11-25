package model.entities;

import model.Entity;
import model.LivingEntity;
import model.config.SimulationConstants;
import model.items.Equipment;
import model.world.SimulationGrid;

/**
 * Base class for humans with infection and settlement behavior.
 */
public abstract class Human extends LivingEntity {
    protected boolean infected = false;
    protected int infectionTimer = 0;
    private static final int FORMATION_RADIUS = SimulationConstants.SETTLEMENT_FORMATION_RADIUS;

    protected char symbol;

    protected Human(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public char getSymbol() {
        if (shouldBecomeZombie()) return 'Z';
        if (infectionTimer > 0) return 'I';
        return symbol;
    }

    public void infect() {
        if (!infected) {
            infected = true;
            infectionTimer = SimulationConstants.INFECTION_TURNS;
        }
    }

    protected void decrementInfectionTimer() {
        if (infectionTimer > 0) infectionTimer--;
    }

    public boolean shouldBecomeZombie() {
        return infected && infectionTimer <= 0;
    }

    protected boolean isInSettlement(SimulationGrid grid) {
        int nearbyHumans = 1;

        for (int dy = -FORMATION_RADIUS; dy <= FORMATION_RADIUS; dy++) {
            for (int dx = -FORMATION_RADIUS; dx <= FORMATION_RADIUS; dx++) {
                int nx = getX() + dx;
                int ny = getY() + dy;
                if (!grid.isValid(nx, ny)) continue;

                Entity entity = grid.get(nx, ny);
                if (entity instanceof Human
                        && (Human) entity != this
                        && ((Human) entity).isPresent()) {
                    Human human = (Human) entity;
                    nearbyHumans++;
                }
            }
        }

        return nearbyHumans >= SimulationConstants.SETTLEMENT_MIN_SIZE;
    }

    protected void moveToFormSettlement(SimulationGrid grid) {
        if (isInSettlement(grid)) {
            return;
        }

        Human nearest = grid.findNearest(getX(), getY(), Human.class);
        if (nearest != null && grid.distanceBetween(this, nearest) > 1) {
            grid.moveToward(nearest.getX(), nearest.getY(), this);
        }
    }

    // ———————— ITEM PICKUP (Shared by Civilian & Soldier) ————————
    protected void tryPickup(SimulationGrid grid) {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                int nx = x + dx, ny = y + dy;
                if (grid.isValid(nx, ny)) {
                    Entity e = grid.get(nx, ny);
                    if (e instanceof Equipment) {
                        Equipment equipment = (Equipment) e;
                        equipment.useOn(this, grid); // Weapon/Armor/Medkit
                        return;
                    }
                }
            }
        }
    }
}
