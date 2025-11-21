public class SadBoid extends Boid{
    @Override
    public double getAttractionFactor() {
        return 0.001;
    }
    public double getMinimumSpeed(){
       return 0.1;
    }

    public double getMaximumSpeed(){
        return 1.0;
    }

    public SadBoid(Vector2D position) {
        super(position);
    }
}
