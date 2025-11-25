package jwp;

/**
 * non-moving, non-infectious corpse marker
 */

public class DeadHost extends HostAgent {

    public DeadHost(int row, int col) {
        super(row, col);
    }

    @Override
    public int getStateCode() {
        return PlagueIncSimulator.DEAD;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public boolean isInfectious() {
        return false;
    }

    @Override
    public boolean canBeInfected() {
        return false;
    }
}
