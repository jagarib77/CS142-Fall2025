
import java.awt.Color;

/*
Obstacle that sits still on the ground
Preston
*/

public class Tree extends Obstacle {
    
    public Tree(Vector2D rootPosition, double width, double height) {
        super(new Vector2D(rootPosition.x, rootPosition.y + height / 2), new Vector2D(width, height));
    }
    
    @Override
    public Color getColor() {
        //brown
        return new Color(102, 51, 0);
    }
}
