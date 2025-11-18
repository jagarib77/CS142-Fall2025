/*
Base class for all obstacles
Preston
*/

public abstract class Obstacle extends SimulationObject {
    public Obstacle(Vector2D position) {
        super(position);
    }
    
    // called when a boid bumps into this obstacle
    public void bump(Boid boid) {
    }
}
