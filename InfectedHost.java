package jwp;

/**
 * infectious host that ages over time and eventually
 * recovers or dies
 */

public class InfectedHost extends HostAgent {

    public InfectedHost(int row, int col) {
        super(row, col);
    }

    @Override
    public int getStateCode() {
        return PlagueIncSimulator.INFECTED;
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public boolean isInfectious() {
        return true;
    }

    @Override
    public boolean canBeInfected() {
        return false;
    }

    @Override
    public double getInfectivityMultiplier() {
        return 1.0; // main full-strength spreaders
    }

    @Override
    public double getSusceptibilityMultiplier() {
        return 1.0; // can still be considered reinfectable or just leave this
    }
}