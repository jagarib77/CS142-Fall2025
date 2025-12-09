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

    @Override
    public double getInfectivityMultiplier() {
        return 0.3; // weaker carrier, tune number as you like (0.2–0.5)
    }

    @Override
    public double getSusceptibilityMultiplier() {
        return 0.0; // cannot be infected again (immune)
    }
}