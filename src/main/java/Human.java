import java.awt.*;

public class Human extends Entity {

    @Override
    public void performAction(Entity[][] grid, double effectRate, int row, int column){
        for (int[] dir : DIRECTIONS) {
            int newRow = row + dir[0];
            int newCol = column + dir[1];

            if (newRow < 0 || newRow >= grid.length || newCol < 0 || newCol >= grid[0].length) continue;

            Entity neighbor = grid[newRow][newCol];
            if (neighbor.isHuman()) {
                if (Math.random() < effectRate) {
                    neighbor.setImmune(true);
                }
            }
        }
    }
    @Override
    public Color getColor() {
        if (this.isImmune())
            return Color.ORANGE;
        return Color.GREEN;
    }
    @Override
    public String toString() { return "H"; }
}
