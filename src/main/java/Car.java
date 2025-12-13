import java.awt.Color;

/*
Obstacle that moves in a straight line
Created by Preston
*/

public class Car extends Obstacle {
    double speed;
    Vector2D direction;
    final double DIAMETER = 4;

    // Initializes the car with a set speed and direction
    public Car(Vector2D position, double speed) {
        super(position);
        this.speed = speed;
        setSize(new Vector2D(DIAMETER, DIAMETER));

        int dir = (int)(Math.random() * 4);
        if(dir == 0) {
            this.direction = new Vector2D(1, 0);
        } else if(dir == 1) {
            this.direction = new Vector2D(-1, 0);
        } else if(dir == 2) {
            this.direction = new Vector2D(0, 1);
        } else {
            this.direction = new Vector2D(0, -1);
        }
    }

    // Updates the car's position based on its speed
    @Override
    public void update(double speedMultiplier) {
        position = position.plus(direction.times(speed * speedMultiplier));

        if(position.x < 0 || position.x > 40) {
            direction.x = -direction.x;
            position.x = Math.max(0, Math.min(40, position.x));
        }
        if(position.y < 0 || position.y > 40) {
            direction.y = -direction.y;
            position.y = Math.max(0, Math.min(40, position.y));
        }
    }

    // returns the color of the car
    @Override
    public Color getColor() {
        return Color.RED;
    }
}