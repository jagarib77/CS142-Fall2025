import java.awt.Color;

public class Entity {
    private int row;
    private int col;
    private boolean infected;
    private boolean immune;

    public Entity(int row, int col) {
        this.row = row;
        this.col = col;
        this.infected = false;
        this.immune = false;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setInfected(boolean infected) {
        this.infected = infected;
    }

    public void setImmune(boolean immune) {
        this.immune = immune;
    }

    public void move(Entity[][] grid) {
    }

    public void interact(Entity[][] grid) {
    }

    public Color getColor() {
        return Color.WHITE;
    }

    public boolean isInfected() {
        return infected;
    }

    public boolean isImmune() {
        return immune;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Entity[][] getNeighbors(Entity[][] grid) {
        Entity[][] neighbors = new Entity[3][3];
        for (int r = -1; r <= 1; r++) {
            for (int c = -1; c <= 1; c++) {
                int newRow = row + r;
                int newCol = col + c;
                if (isInBounds(newRow, newCol, grid)) {
                    neighbors[r + 1][c + 1] = grid[newRow][newCol];
                } else {
                    neighbors[r + 1][c + 1] = null;
                }
            }
        }
        return neighbors;
    }

    public boolean isInBounds(int r, int c, Entity[][] grid) {
        return r >= 0 && r < grid.length && c >= 0 && c < grid[0].length;
    }

}
