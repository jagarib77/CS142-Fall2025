import java.awt.*;

// Created by Apollo
// The SadBoid class is a class that represents a boid that moves slowly and avoids other boids
public class SadBoid extends Boid{
    // Returns a negative value to make the boid move away from other boids
    @Override
    public double getAttractionFactor() {
        return -0.01;
    }


    // These get the minimum and maximum speeds for the boid
    public double getMinimumSpeed(){
       return 0.1;
    }
    public double getMaximumSpeed(){
        return 0.2;
    }

    // Creates the boids at the given position
    public SadBoid(Vector2D position, int gridRows, int gridCols) {
        super(position, gridRows, gridCols);
    }

    // Gets the color of the boid
    @Override
    public Color getColor() {
        return Color.blue;
    }
}
