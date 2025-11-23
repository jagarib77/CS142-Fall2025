
import java.awt.Graphics2D;

/*
Base class for all obstacles
Preston
*/

public abstract class Obstacle extends SimulationObject {
    Vector2D size;
    
    public Obstacle(Vector2D position, Vector2D size) {
        super(position);
        this.size = size;
    }
    
    // called when a boid bumps into this obstacle
    public void bump(Boid boid) {
    }
    
    public void draw(SimulationGUI simulationGUI, Graphics2D g2) {
        simulationGUI.paintGridPixel((int)position.x,
                                     (int)position.y,
                                     (int)size.x,(int)size.y,color, g2);
    }
    
    public boolean isOverlapped(Vector2D point) {
        double left = point.x - size.x / 2;
        if (point.x < left) {
            return false;
        }
        double right = point.x + size.x / 2;
        if (point.x > right) {
            return false;
        }
        double top = point.y - size.y / 2;
        if (point.y < top) {
            return false;
        }
        double bottom = point.y + size.y / 2;
        if (point.y > bottom) {
            return false;
        }
        return true;
    }
    
    public Vector2D getSize() {
        return size;
    }
}
