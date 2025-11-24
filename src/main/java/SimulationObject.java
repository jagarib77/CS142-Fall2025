
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
    
    public SimulationObject(Vector2D position) {
        this.position = position;
    }
    
    public Vector2D getPosition() {
        return position;
    }
    
    public void update() {

    }
    
    public void draw(SimulationGUI simulationGUI, Graphics2D g2) {
        simulationGUI.paintGridPixel((int)position.x,
                                     (int)position.y,
                                     1,1,getColor(), g2);
    }
    
    public Color getColor() {
        return new Color(0,0,0);
    }
}
