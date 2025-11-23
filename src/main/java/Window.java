
import java.awt.Color;

/*
Obstacle that sits still, and boids can't see
Preston
*/

public class Window extends Obstacle {
    public Window(Vector2D position, double diameter) {
        super(position, new Vector2D(diameter, diameter));
    }
    
    @Override
    public Color getColor() {
        //cyan
        return Color.CYAN;
    }
}
