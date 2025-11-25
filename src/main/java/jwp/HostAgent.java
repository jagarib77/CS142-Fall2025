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
}
