package jwp;

/**
 * Intermediate base class for all human hosts in the simulation.
 * Extends PlagueIncAgent with an infectionAge counter that is used by
 * infected hosts to determine when they recover or die.
 */

public abstract class HostAgent extends PlagueIncAgent {

    // Number of steps spent in the current infection (only used when infected)
    protected int infectionAge;

    public HostAgent(int row, int col) {
        super(row, col);
        this.infectionAge = 0;
    }

    public int getInfectionAge() {
        return infectionAge;
    }

    public void resetInfectionAge() {
        infectionAge = 0;
    }

    public void incrementInfectionAge() {
        infectionAge++;
    }

    /**
     * Returns how infectious this host is.
     * 0.0 = not infectious, 1.0 = fully infectious.
     */
    public double getInfectivityMultiplier() {
        return 0.0; // default: non-infectious
    }

    /**
     * Returns how susceptible this host is to infection.
     * 0.0 = cannot be infected, 1.0 = fully susceptible.
     */
    public double getSusceptibilityMultiplier() {
        return 1.0; // default: fully susceptible
    }

    /**
     * Convenience: can this host be infected at all?
     */
    public boolean canBeInfected() {
        return getSusceptibilityMultiplier() > 0.0;
    }

    /**
     * Convenience: is this host currently infectious?
     */
    public boolean isInfectious() {
        return getInfectivityMultiplier() > 0.0;
    }
}