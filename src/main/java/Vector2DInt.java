/*
Similar to Vector2D, but has integer coordinates to accomodate the grid-based structure
*/

class Vector2DInt {
    int x = 0;
    int y = 0;
    
    // Constructor that assigns x and y
    public Vector2DInt(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}
