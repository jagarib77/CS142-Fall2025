
import java.awt.Color;

/*
Obstacle that moves in a straight line
Preston
*/

public class Car extends Obstacle {
    double speed;
    final double DIAMETER = 4;
    
    public Car(Vector2D position, double speed) {
        super(position);
        this.speed = speed;
        setSize(new Vector2D(DIAMETER, DIAMETER));
    }
    
    @Override
    public Color getColor() {
        //red
        return Color.RED;
    }
}
