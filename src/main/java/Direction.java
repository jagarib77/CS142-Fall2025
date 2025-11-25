/*
CS&142
Author : Thaknin Hor
Date: 11/25/2005
 */

package OceanPath;

public class Direction {
    public String name;
    public Integer dx;
    public Integer dy;

    public Direction(String name, Integer dx, Integer dy) {
        this.name = name;
        this.dx = dx;
        this.dy = dy;
    }

    public static final Direction N = new Direction("N", 0, -1);
    public static final Direction NE = new Direction("NE", 1, -1);
    public static final Direction E = new Direction("E", 1, 0);
    public static final Direction S = new Direction("S", 0, 1);
    public static final Direction SE = new Direction("SE", 1, 1);
    public static final Direction W = new Direction("W", -1, 0);
    public static final Direction SW = new Direction("SW", -1, 1);
    public static final Direction NW = new Direction("NW", -1, -1);
    public static final Direction C = new Direction("C", 0, 0);

    public String toString(){
        switch(name) {
            case "N": return " ↑ ";
            case "S": return " ↓ ";
            case "W": return " ← ";
            case "E": return " → ";
            case "NE": return " ↗ ";
            case "SE": return " ↘ ";
            case "SW": return " ↙ ";
            case "NW": return " ↖ ";
            default: return name;
        }
    }

}
