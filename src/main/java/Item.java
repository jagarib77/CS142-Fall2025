// CS 142
// AUTHOR: Afnan Ali
// 11/24/25

package OceanPath;

public class Item {

    private int x;
    private int y;

    public Item(int startX, int startY){
        x = startX;
        y = startY;
    }

    public int getX() { 
        return x;
    }

    public int getY(){
        return y;
    }

    public void moveBy(Direction d, int maxRows, int maxCols) {
        if (d == null) {
            return;
        }

        int newRow = y + d.dy;
        int newCol = x + d.dx;

        x = newCol;
        y = newRow;
    }
}
