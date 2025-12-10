import java.awt.*;

public class ChosenOne extends Human {
    // TODO: not sure what action to do *maybe make neighbors immune*
    // TODO: needs getColor(), toString(), performAction(), isImmune()
    public  void performAction(Entity[][] grid, double effectRate, int row, int column){

        for (int[] dir : DIRECTIONS) {
            int newRow = row + dir[0];
            int newColumn = column + dir[1];

            // Check bounds of grid
            if (newRow < 0 || newRow >= grid.length || newColumn < 0 || newColumn >= grid[0].length) continue;

            Entity neighbor = grid[newRow][newColumn];

            if (neighbor instanceof Human) {
                if (Math.random() < effectRate) {
                    grid[newRow][newColumn].setImmune(true);
                }
            }
        }
    }
    @Override
    public boolean isImmune() { return true;}


    @Override
    public Color getColor() { return Color.BLUE; }

    @Override
    public String toString() { return "C"; }

}
