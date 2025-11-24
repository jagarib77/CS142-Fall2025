import java.awt.*;

public class SuperBoid extends Boid{
    @Override
    public double getAttractionFactor() {
        return 0.1;
    }
    public double getMinimumSpeed(){
       return 0.1;
    }

    public double getMaximumSpeed(){
        return 1.0;
    }

    public SuperBoid(Vector2D position, int gridRows, int gridCols) {
        super(position, gridRows, gridCols);
    }

    @Override
    public Color getColor() {
        return Color.MAGENTA;
    }
}

