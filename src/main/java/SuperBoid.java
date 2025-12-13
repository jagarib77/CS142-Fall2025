import java.awt.*;

// Created by Apollo
// A Boid that Moves faster and attracts other boids more
public class SuperBoid extends Boid{
    // Returns how attractive other boids are towards this boid
    @Override
    public double getAttractionFactor() {
        return 0.1;
    }

    // Returns the minimum and maximum speeds for the boid
    public double getMinimumSpeed(){
       return 0.1;
    }
    public double getMaximumSpeed(){
        return 1.0;
    }

    // Creates the boids at the given position
    public SuperBoid(Vector2D position, int gridRows, int gridCols) {
        super(position, gridRows, gridCols);
    }

    // Gets the color of the boid
    @Override
    public Color getColor() {
        return Color.MAGENTA;
    }
}

