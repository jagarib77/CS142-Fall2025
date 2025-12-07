import java.awt.*;

public class Medic extends Human {
    @Override       // TODO: finish
    public void performAction(Entity[][] grid, double effectRate, int row, int column){

        double healRate = effectRate / 100.0;
        for (int[] dir : DIRECTIONS) {
            int newRow = row + dir[0];
            int newColumn = column + dir[1];

            // Check bounds of grid
            if (newRow < 0 || newRow >= grid.length || newColumn < 0 || newColumn >= grid[0].length) continue;

            Entity neighbor = grid[newRow][newColumn];
            //make sure it's human and NOT immune
            if (!neighbor.isHuman()) {
                if (Math.random() < effectRate) {
                    grid[newRow][newColumn] = new Human();  // chance to infect human
                }
            }
        }
    }

    @Override
    public Color getColor() { return Color.YELLOW; }

    @Override
    public String toString() { return "M"; }
}
