/*
A class that represents a 2D vector
Preston Campbell
*/

public class Vector2D {
    double x;
    double y;
    
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public Vector2D normalize() {
        double _magnitude = magnitude();
        x /= _magnitude;
        y /= _magnitude;
        return this;
    }
    
    public Vector2D normalized() {
        double _magnitude = magnitude();
        return new Vector2D(x / _magnitude, y / _magnitude);
    }
    
    
    public double magnitude() {
        return Math.sqrt(x*x + y*y);
    }
    
    public Vector2D rotateDeg(double degrees) {
        return rotateRad(Math.toRadians(degrees));
    }
    
    public Vector2D rotateRad(double radians) {        
        double cs = Math.cos(radians);
        double sn = Math.sin(radians);
                
        double _x = x * cs - y * sn;
        double _y = x * sn + y * cs;
        
        x = _x;
        y = _y;
        
        return this;
    }
}
