/*
Base class for all objects in the simulation
Apollo/Preston
*/

class SimulationObject {
    Vector2DInt position;
    
    public SimulationObject(Vector2DInt position) {
        this.position = position;
    }
    
    public Vector2DInt getPosition() {
        return position;
    }
    
    public void update() {
    }
}
