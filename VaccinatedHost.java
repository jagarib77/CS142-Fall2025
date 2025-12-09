package jwp;

/**
 * Immune or highly resistant to infection
 */

public class VaccinatedHost extends HostAgent {

    public VaccinatedHost(int row, int col) {
        super(row, col);
    }

    @Override
    public int getStateCode() {
        return PlagueIncSimulator.VACCINATED;
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
        return false; // or mostly false if you later add breakthrough infections
    }

    @Override
    public double getInfectivityMultiplier() {
        return 0.0; // they don't spread on their own
    }

    @Override
    public double getSusceptibilityMultiplier() {
        return 0.2; // only 20% as likely to get infected
        // (tune this: 0.1 = very strong vaccine, 0.5 = weaker)
    }
}