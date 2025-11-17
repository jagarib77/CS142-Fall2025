/*
Base class for all obstacles
*/

public abstract class Obstacle {
    Vector2DInt position;
    
    public Obstacle(Vector2DInt position) {
        this.position = position;
    }
    
    // called when a boid bumps into this obstacle
    public void bump(Boid boid) {
    }
}
