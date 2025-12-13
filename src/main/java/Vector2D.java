/*
A class that represents a 2D vector
Preston Campbell
*/

public class Vector2D {
    double x = 0;
    double y = 0;
    
    // Constructor that assigns x and y
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Constructor that leaves x and y as 0
    public Vector2D() {

    }

    // Getters and setters for x and y components
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setX(double x) {
        this.x = x; 
    }
    public void setY(double y) {
        this.y = y; 
    }
    
    // Make the vector have a magnitude of 1
    public Vector2D normalize() {
        double _magnitude = magnitude();
        x /= _magnitude;
        y /= _magnitude;
        return this;
    }
    
    // Returns a vector with a magnitude of 1 without changing the variables in the class
    public Vector2D normalized() {
        double _magnitude = magnitude();
        return new Vector2D(x / _magnitude, y / _magnitude);
    }

    // Returns the magnitude of the vector, using the distance formula
    public double magnitude() {
        return Math.sqrt(x*x + y*y);
    }

    // Returns the vector but rotated by a specific angle in degrees
    public Vector2D rotateDeg(double degrees) {
        return rotateRad(Math.toRadians(degrees));
    }

    // Returns the vector but rotated by a specific angle in radians
    public Vector2D rotateRad(double radians) {        
        double cs = Math.cos(radians);
        double sn = Math.sin(radians);
                
        double _x = x * cs - y * sn;
        double _y = x * sn + y * cs;
        
        x = _x;
        y = _y;
        
        return this;
    }

    // Returns the distance between two vectors
    public double distanceTo(Vector2D vector) {
        return Math.sqrt(Math.pow(x - vector.getX(), 2) + Math.pow(y - vector.getY(), 2));
    }


    // returns a vector that is the sum of this vector and the given vector
    public Vector2D plus(Vector2D vector) {
        return new Vector2D(x + vector.getX(), y + vector.getY());
    }

    // returns a vector that is the difference of this vector and the given vector
    public Vector2D minus(Vector2D vector) {
        return new Vector2D(x - vector.getX(), y - vector.getY());
    }

    // returns a vector that is the product of this vector and the given scalar
    public Vector2D times(double t) {
        return new Vector2D(x * t, y * t);
    }

    // Converts this vector a string in the form of (x, y)
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
