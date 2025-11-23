
import java.awt.Color;

/*
Obstacle that sits still, and boids can't see
Preston
*/

public class Window extends Obstacle {
    public Window(Vector2D position, Vector2D size) {
        super(position, size);
    }
    
    @Override
    public Color getColor() {
        //cyan
        return Color.CYAN;
    }
}
