/*
Obstacle that moves in a straight line
Preston
*/

public class Car extends Obstacle {
    double speed;
    
    public Car(Vector2D position, Vector2D size, double speed) {
        super(position, size);
        this.speed = speed;
    }
}
