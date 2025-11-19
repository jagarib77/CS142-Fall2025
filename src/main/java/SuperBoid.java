public class SuperBoid extends Boid{
    @Override
    public double getAttractionFactor() {
        return 0.1;
    }
    public double getMinimumSpeed(){
       return 2.0;
    }

    public double getMaximumSpeed(){
        return 10.0;
    }

    public SuperBoid(Vector2D position) {
        super(position);
    }
}
