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

        int newRow = y + d.dx;
        int newCol = x + d.dy;

        x = newCol;
        y = newRow;
    }

    public void setPos (int x, int y){
        this.x = x;
        this.y = y;

    }



}
