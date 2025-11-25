// Afnan Ali. CS 142. FALL 2025
// 11/3/2025
// Location.java
// Stores a single location with x, y, and elevation

import java.util.*;


public class Location {
    
    private final int y;
    private final int x;
    private final List<Direction> directions = new ArrayList<>();
    private final Random rnd = new Random();

    public Location(int row, int col){
        x = col;
        y = row;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void addDirection(Direction d) {
        if (d!= null) {
            directions.add(d);            
        }
    }

    public void clearDirections(){
        directions.clear();
    }





}
    

