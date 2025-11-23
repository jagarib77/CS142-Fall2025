
import java.awt.Color;

/*
Obstacle that sits still on the ground
Preston
*/

public class Tree extends Obstacle {
    
    final double WIDTH = 1;
    
    public Tree(Vector2D rootPosition, double height) {
        super(new Vector2D(rootPosition.x, rootPosition.y + height / 2));
        setSize(new Vector2D(WIDTH, height));
    }
    
    @Override
    public Color getColor() {
        //brown
        return new Color(102, 51, 0);
    }
}
