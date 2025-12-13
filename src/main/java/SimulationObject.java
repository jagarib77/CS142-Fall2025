
import java.awt.Color;
import java.awt.Graphics2D;

/*
Base class for all objects in the simulation
Apollo/Preston
*/

class SimulationObject {
    Vector2D position = new Vector2D(0,0);
    int gridRows;
    int gridCols;

    // Creates an object at the given position
    public SimulationObject(Vector2D position) {
        this.position = position;
    }

    // Returns the position of the object
    public Vector2D getPosition() {
        return position;
    }

    // An empty function that can be overridden by subclasses, updates the object for every simulation step
    public void update() {

    }

    // Updates the object for every simulation step, with the given speed multiplier
    public void update(double speedMultiplier) {
    update(); 
}

    // Draws the object on the screen
    public void draw(SimulationGUI simulationGUI, Graphics2D g2) {
        simulationGUI.paintGridPixel((int)position.x,
                                     (int)position.y,
                                     1,1,getColor(), g2);
    }

    // Gets the color of the object
    public Color getColor() {
        return new Color(0,0,0);
    }
}
