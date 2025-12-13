
import java.awt.Color;

/*
Obstacle that sits still, and boids can't see
Preston
*/

public class Window extends Obstacle {
    // Creates a window at the given position
    public Window(Vector2D position, double diameter) {
        super(position, new Vector2D(diameter, diameter));
    }


    // Gets the color of the window
    @Override
    public Color getColor() {
        //cyan
        return Color.CYAN;
    }
}
