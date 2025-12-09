package jwp;

public interface GridSimulator {

    /**
     * Runs one simulation tick.
     * states[y][x] is the cell state.
     */
    void tick(int[][] states, int width, int height);

    /**
     * Returns cure progress as a value from 0.0 to 1.0.
     * GridPanel uses this to update the JProgressBar.
     */
    double getCureProgress();

    /**
     * Whether the simulation has reached a finished state.
     * (Optional but helpful for stopping timers.)
     */
    default boolean isGameOver() {
        return false;
    }

    /**
     * A message GridPanel or MainFrame can display when the simulation ends.
     */
    default String getEndMessage() {
        return "";
    }
}
