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

    public double getInfectivityMultiplier() {
        return 0.0; // healthy people do NOT spread
    }

    @Override
    public double getSusceptibilityMultiplier() {
        return 1.0; // fully susceptible
    }

    // getStateCode() returns HEALTHY, etc.
}