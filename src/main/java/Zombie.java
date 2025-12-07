import java.awt.*;

public class Zombie extends Entity {

    public void performAction(Entity[][] grid, double effectRate, int row, int column) {
            //DIRECTIONS from entity class; checks sorrounding neighbors
            //Checks first neighbor {-1, 0} -> {row -1, column + 0}...
            double infectionRate = effectRate / 100.0;
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newColumn = column + dir[1];

                // Check bounds of grid
                if (newRow < 0 || newRow >= grid.length || newColumn < 0 || newColumn >= grid[0].length) continue;

                Entity neighbor = grid[newRow][newColumn];
                //make sure it's human and NOT immune
                if (!neighbor.isImmune() && neighbor.isHuman()) {
                    if (Math.random() < effectRate) {
                        grid[newRow][newColumn] = new Zombie();  // chance to infect human
                    }
                }
            }
    }

    public Color getColor() { return Color.red; }

    public boolean isHuman() { return false;}
    @Override
    public String toString() { return "Z"; }
}
