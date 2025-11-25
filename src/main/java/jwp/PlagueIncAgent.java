package jwp;

/**
 * Abstract base class for anything that can appear in the plague grid.
 * Stores a row/column position and requires subclasses to provide a
 * state code for drawing, as well as basic behavior flags like
 * isAlive, isInfectious, and canBeInfected.
 */

public abstract class PlagueIncAgent {

    protected int row;
    protected int col;

    public PlagueIncAgent(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    // Used to map this agent to an int state for GridPanel
    public abstract int getStateCode();

    // For movement/logic
    public abstract boolean isAlive();

    public abstract boolean isInfectious();

    public abstract boolean canBeInfected();
}
