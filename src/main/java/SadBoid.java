import java.awt.*;

public class SadBoid extends Boid{
    @Override
    public double getAttractionFactor() {
        return -0.01;
    }
    public double getMinimumSpeed(){
       return 0.1;
    }

    public double getMaximumSpeed(){
        return 0.2;
    }

    public SadBoid(Vector2D position, int gridRows, int gridCols) {
        super(position, gridRows, gridCols);
    }

    @Override
    public Color getColor() {
        return Color.blue;
    }
}
