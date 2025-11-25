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
}
