/*
Obstacle that sits still on the ground
Preston
*/

public class Tree extends Obstacle {
    
    public Tree(Vector2D rootPosition, Vector2D size) {
        super(new Vector2D(rootPosition.x, rootPosition.y + size.y / 2), size);
    }
}
