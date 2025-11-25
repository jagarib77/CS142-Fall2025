package jwp;

public interface GridSimulator {
    // Called every tick; states[y][x] is the cell state
    void tick(int[][] states, int width, int height);
}
