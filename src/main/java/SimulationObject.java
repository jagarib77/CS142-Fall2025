/*
Base class for all objects in the simulation
Apollo/Preston
*/

class SimulationObject {
    Vector2D position;
    
    public SimulationObject(Vector2D position) {
        this.position = position;
    }
    
    public Vector2D getPosition() {
        return position;
    }
    
    public void update() {
    }
}
