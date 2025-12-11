package jwp;
/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Class         DeadHost
 * File          DeadHost.java
 * Description   Represents a host that died from the disease. Dead hosts do not
 *               move, cannot be infected again, and do not contribute to further
 *               spread, but they are counted in statistics and help drive cure
 *               progress by increasing the visible severity of the outbreak.
 * @author       <i>Landon Carothers</i>
 * Environment   Java SE 24, NetBeans IDE 25, Windows 10
 * Date          11/25/2025
 * @version      1.0
 * @see          HostAgent
 * @see          PlagueIncSimulator
 *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
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

    @Override
    public double getInfectivityMultiplier() {
        return 0.0; // dead, no spreading
    }

    @Override
    public double getSusceptibilityMultiplier() {
        return 0.0; // cannot be infected
    }
}