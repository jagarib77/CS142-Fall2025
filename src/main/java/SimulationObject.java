
import java.awt.Color;
import java.awt.Graphics2D;

/*
Base class for all objects in the simulation
Apollo/Preston
*/

class SimulationObject {
    Vector2D position = new Vector2D(0,0);
    Color color;
    
    public SimulationObject(Vector2D position) {
        this(position, new Color(0,0,0));
    }
    
    public SimulationObject(Vector2D position, Color color) {
        this.position = position;
        this.color = color;
    }
    
    public Vector2D getPosition() {
        return position;
    }
    
    public void update() {

    }
    
    public void draw(SimulationGUI simulationGUI, Graphics2D g2) {
        simulationGUI.paintGridPixel((int)position.x,
                                     (int)position.y,
                                     1,1,color, g2);
    }
}
