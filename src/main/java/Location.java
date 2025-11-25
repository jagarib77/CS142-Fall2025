//CS142
//Author : Afnan Ali
//Date : 11/24/2025

package OceanPath;

import java.util.*;


public class Location {
    
    private final int x;
    private final int y;
    private final List<Direction> directions = new ArrayList<>();
    private final Random r = new Random();

    public Location(int x, int y){
        this.x = x;
        this.y = y;
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

    public List<Direction> getDirections(){
        return new ArrayList<>(directions);
    }

    public Direction chooseRandomDirection(){

        return directions.get(r.nextInt(directions.size()));
    }
}
