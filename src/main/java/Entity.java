import java.awt.*;

public abstract class Entity {
    //checks all possible sorrounding squares with
    //each direction.
    protected static final int[][] DIRECTIONS = {
            {-1, 0},  {1, 0},   {0, -1}, {0, 1},
            {-1, 1},  {-1, -1}, {1, 1},  {1, -1}
    };

    //@override infect or defend depending on object
    public abstract void performAction(Entity[][] grid, double effectRate, int row, int column);

    //each class gets its own unique color
    public abstract Color getColor();


    // default = false, subclasses override what they are. Only zombie classes need to override
    public boolean isHuman() { return true; }

    //only chosen one is immune
    public boolean isImmune() { return false;}
}
