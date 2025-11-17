/*
Obstacle that moves in a straight line
Preston
*/

public class Car extends Obstacle {
    double speed;
    
    public Car(Vector2DInt position, double speed) {
        super(position);
        this.speed = speed;
    }
}
