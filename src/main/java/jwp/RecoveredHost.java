package jwp;

/**
 * No longer infectious and immune to reinfection
 */

public class RecoveredHost extends HostAgent {

    public RecoveredHost(int row, int col) {
        super(row, col);
    }

    @Override
    public int getStateCode() {
        return PlagueIncSimulator.RECOVERED;
    }

    @Override
    public boolean isAlive() {
        return true;
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
