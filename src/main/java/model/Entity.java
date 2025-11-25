package model;

/**
 * Base class for all objects in the world.
 */
public abstract class Entity {
    protected int x;
    protected int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract char getSymbol();
    public abstract boolean isAlive();
}
