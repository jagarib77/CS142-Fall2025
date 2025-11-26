package service;

import model.entities.Entity;
import model.entities.*;
import model.world.WorldGrid;

public class GameStateService {
    private final WorldGrid grid;
    private String winner = null;

    public GameStateService(WorldGrid grid) {
        this.grid = grid;
    }

    public boolean checkForWinner() {
        boolean hasHuman = false, hasZombie = false;
        for (Entity[] row : grid.raw()) {
            for (Entity e : row) {
                if (e instanceof Human && ((Human) e).isPresent() && !((Human) e).shouldBecomeZombie()) {
                    Human h = (Human) e;
                    hasHuman = true;
                }
                if (e instanceof Zombie && ((Zombie) e).isPresent()) {
                    Zombie z = (Zombie) e;
                    hasZombie = true;
                }
            }
        }
        if (!hasHuman || !hasZombie) {
            winner = hasHuman ? "HUMANS WIN!" : "ZOMBIES WIN!";
            System.out.println("\nGAME OVER! " + winner);
            return true;
        }
        return false;
    }

    public int countHumans() {
        int count = 0;
        for (Entity[] row : grid.raw()) {
            for (Entity e : row) {
                if (e instanceof Human && ((Human) e).isPresent() && !((Human) e).shouldBecomeZombie()) {
                    Human h = (Human) e;
                    count++;
                }
            }
        }
        return count;
    }

    public int countZombies() {
        int count = 0;
        for (Entity[] row : grid.raw()) {
            for (Entity e : row) {
                if (e instanceof Zombie && ((Zombie) e).isPresent()) {
                    Zombie z = (Zombie) e;
                    count++;
                }
            }
        }
        return count;
    }

    public int countEntitiesBySymbol(char symbol) {
        int count = 0;
        for (Entity[] row : grid.raw())
            for (Entity e : row)
                if (e != null && e.getSymbol() == symbol && e.isPresent())
                    count++;
        return count;
    }

    public String getWinner() {
        return winner;
    }
}