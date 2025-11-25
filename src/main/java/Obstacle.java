
import java.awt.Graphics2D;

/*
Base class for all obstacles
Preston
*/

public abstract class Obstacle extends SimulationObject {
    Vector2D size = new Vector2D(1,1);
    
    public Obstacle(Vector2D position, Vector2D size) {
        super(position);
        this.size = size;
    }
    
    public Obstacle(Vector2D position) {
        super(position);
    }
    
    // called when a boid bumps into this obstacle
    public void bump(Boid boid) {
    }
    
    public void draw(SimulationGUI simulationGUI, Graphics2D g2) {
        simulationGUI.paintGridPixel((int)position.x,
                                     (int)position.y,
                                     (int)size.x,(int)size.y,getColor(), g2);
    }
    
    public boolean isOverlapped(Vector2D point) {
    double left = position.x - size.x / 2;
    double right = position.x + size.x / 2;
    double top = position.y - size.y / 2;
    double bottom = position.y + size.y / 2;
    
    return point.x >= left && point.x <= right && 
           point.y >= top && point.y <= bottom;
}
    
    public Vector2D getSize() {
        return size;
    }
    
    public void setSize(Vector2D size) {
        this.size = size;
    }
}
