package model.world;

import java.util.Random;

/**
 * Cardinal directions with utility method to get direction between two points.
 */
public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    private final int dy;
    private final int dx;

    Direction(int dy, int dx) {
        this.dy = dy;
        this.dx = dx;
    }

    public int dy() {
        return dy;
    }

    public int dx() {
        return dx;
    }

    /**
     * Returns the Direction from (fromX, fromY) toward (toX, toY).
     * Prefers vertical to horizontal if diagonal.
     */
    public static Direction toward(int fromX, int fromY, int toX, int toY) {
        int dy = Integer.signum(toY - fromY);
        int dx = Integer.signum(toX - fromX);

        if (dy == -1) return UP;
        if (dy == 1) return DOWN;
        if (dx == -1) return LEFT;
        if (dx == 1) return RIGHT;

        return UP;
    }

    /**
     * Returns a random cardinal direction (UP, DOWN, LEFT, RIGHT).
     */
    public static Direction random(Random rng) {
        return values()[rng.nextInt(4)];
    }
}