package jwp;

/**
 * Represents a healthy (susceptible) host. Alive, not infectious,
 * and can become infected when exposed to nearby infected hosts.
 */

public class HealthyHost extends HostAgent {

    public HealthyHost(int row, int col) {
        super(row, col);
    }

    @Override
    public int getStateCode() {
        return PlagueIncSimulator.HEALTHY;
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
        return true;
    }
}
