
import java.awt.Color;

/*
Obstacle that moves in a straight line
Preston
*/

public class Car extends Obstacle {
    double speed;
    
    public Car(Vector2D position, double diameter, double speed) {
        super(position, new Vector2D(diameter, diameter));
        this.speed = speed;
    }
    
    @Override
    public Color getColor() {
        //red
        return Color.RED;
    }
}
