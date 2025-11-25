//CS142
//Author : Afnan Ali & Thaknin Hor
//Date : 11/24/2025

package OceanPath;

import java.util.*;

public class Location {

    private final int x;
    private final int y;
    private Direction direction;
    private final Random r = new Random();

    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void addDirection(Direction d) {
        this.direction = d;
    }

    public Direction getDirection() {
        return direction;
    }

    public Direction chooseRandomDirection() {
        if (direction.equals(Direction.N)) {
            Direction[] options = {Direction.N, Direction.NE, Direction.NW};
            return options[r.nextInt(options.length)];
        } else if (direction.equals(Direction.E)) {
            Direction[] options = {Direction.E, Direction.NE, Direction.SE};
            return options[r.nextInt(options.length)];
        } else if (direction.equals(Direction.S)) {
            Direction[] options = {Direction.S, Direction.SE, Direction.SW};
            return options[r.nextInt(options.length)];
        } else if (direction.equals(Direction.W)) {
            Direction[] options = {Direction.W, Direction.SW, Direction.NW};
            return options[r.nextInt(options.length)];
        } else if (direction.equals(Direction.NE)) {
            Direction[] options = {Direction.NE, Direction.N, Direction.E};
            return options[r.nextInt(options.length)];
        } else if (direction.equals(Direction.SE)) {
            Direction[] options = {Direction.SE, Direction.S, Direction.E};
            return options[r.nextInt(options.length)];
        } else if (direction.equals(Direction.SW)) {
            Direction[] options = {Direction.SW, Direction.S, Direction.W};
            return options[r.nextInt(options.length)];
        } else if (direction.equals(Direction.NW)) {
            Direction[] options = {Direction.NW, Direction.N, Direction.W};
            return options[r.nextInt(options.length)];
        } else {
            return Direction.C;
        }
    }
}
    

