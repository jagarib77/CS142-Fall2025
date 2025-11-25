package jwp;

import java.awt.Color;
import java.util.Random;

/**
 * Controls the Plague Inc–style simulation.
 * Manages a grid of PlagueIncAgent objects, updates them each tick
 * (movement, infection spread, recovery, death), and writes state codes
 * into the int[][] grid used by GridPanel for drawing.
 */

public class PlagueIncSimulator implements GridSimulator {

    // Cell state codes for GridPanel
    public static final int EMPTY      = 0;
    public static final int HEALTHY    = 1;
    public static final int INFECTED   = 2;
    public static final int RECOVERED  = 3;
    public static final int VACCINATED = 4;
    public static final int DEAD       = 5;

    // Simulation parameters (tweak as you like)
    private double density               = 0.40;  // fraction of grid initially occupied
    private double initialInfectedFrac   = 0.02;  // fraction of population starting infected
    private double initialVaccinatedFrac = 0.10;  // fraction of population starting vaccinated

    private double infectionProbability  = 0.25;  // chance a HEALTHY neighbor becomes INFECTED
    private double movementProbability   = 0.70;  // chance a living host tries to move each tick
    private double deathProbability      = 0.20;  // when infection resolves, chance of death

    private int minInfectionDuration     = 10;    // steps before infection can resolve
    private int maxInfectionDuration     = 30;    // max steps before it basically must resolve

    private PlagueIncAgent[][] agents;           // main simulation grid
    private final Random rand = new Random();

    @Override
    public void tick(int[][] states, int width, int height) {
        if (states == null) {
            return;
        }

        // (Re)initialize agent grid if needed
        if (agents == null || agents.length != height || agents[0].length != width) {
            initAgents(width, height);
        }

        // 1. Movement: build next agent grid
        PlagueIncAgent[][] next = new PlagueIncAgent[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                PlagueIncAgent agent = agents[y][x];
                if (agent == null) {
                    continue;
                }

                int destX = x;
                int destY = y;

                // living hosts may move
                if (agent.isAlive() && rand.nextDouble() < movementProbability) {
                    int dir = rand.nextInt(4); // 0=right,1=left,2=down,3=up
                    int nx = x;
                    int ny = y;
                    switch (dir) {
                        case 0: nx = x + 1; break;
                        case 1: nx = x - 1; break;
                        case 2: ny = y + 1; break;
                        case 3: ny = y - 1; break;
                    }

                    if (nx >= 0 && nx < width && ny >= 0 && ny < height 
                            && next[ny][nx] == null) {
                        destX = nx;
                        destY = ny;
                    }
                }

                // place in next grid; fall back to original cell on conflict
                if (next[destY][destX] == null) {
                    agent.setPosition(destY, destX);
                    next[destY][destX] = agent;
                } else {
                    // try to stay in original position
                    if (next[y][x] == null) {
                        agent.setPosition(y, x);
                        next[y][x] = agent;
                    }
                    // else: this agent is effectively removed due to overcrowding
                }
            }
        }

        // Use moved agents as current state for infection logic
        agents = next;

        // 2. Infection spread
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                PlagueIncAgent agent = agents[y][x];
                if (agent instanceof HostAgent) {
                    HostAgent host = (HostAgent) agent;
                    if (host.isInfectious()) {
                        infectNeighborIfHealthy(x + 1, y, width, height);
                        infectNeighborIfHealthy(x - 1, y, width, height);
                        infectNeighborIfHealthy(x, y + 1, width, height);
                        infectNeighborIfHealthy(x, y - 1, width, height);
                    }
                }
            }
        }

        // 3. Infection progression (age, recovery, death)
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                PlagueIncAgent agent = agents[y][x];
                if (agent instanceof InfectedHost) {
                    HostAgent infected = (HostAgent) agent;
                    infected.incrementInfectionAge();
                    int age = infected.getInfectionAge();

                    if (age >= minInfectionDuration) {
                        double resolveChance =
                                (double) (age - minInfectionDuration + 1)
                                / (maxInfectionDuration - minInfectionDuration + 1);
                        if (resolveChance > 1.0) {
                            resolveChance = 1.0;
                        }

                        if (rand.nextDouble() < resolveChance) {
                            // infection ends this tick
                            if (rand.nextDouble() < deathProbability) {
                                agents[y][x] = new DeadHost(y, x);
                            } else {
                                agents[y][x] = new RecoveredHost(y, x);
                            }
                        }
                    }
                }
            }
        }

        // 4. Copy agent states into the int[][] for GridPanel to draw
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                PlagueIncAgent agent = agents[y][x];
                if (agent == null) {
                    states[y][x] = EMPTY;
                } else {
                    states[y][x] = agent.getStateCode();
                }
            }
        }
    }

    private void infectNeighborIfHealthy(int x, int y, int width, int height) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return;
        }
        PlagueIncAgent neighbor = agents[y][x];
        if (neighbor instanceof HostAgent) {
            HostAgent host = (HostAgent) neighbor;
            if (host.canBeInfected()) {
                if (rand.nextDouble() < infectionProbability) {
                    agents[y][x] = new InfectedHost(y, x);
                }
            }
        }
    }

    private void initAgents(int width, int height) {
        agents = new PlagueIncAgent[height][width];

        int totalCells = width * height;
        int targetPopulation   = (int) (totalCells * density);
        int initialInfected    = (int) (targetPopulation * initialInfectedFrac);
        int initialVaccinated  = (int) (targetPopulation * initialVaccinatedFrac);

        int placedInfected = 0;
        int placedVaccinated = 0;
        int placedHealthy = 0;

        // place hosts randomly in the grid
        while (placedHealthy + placedInfected + placedVaccinated < targetPopulation) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);

            if (agents[y][x] != null) {
                continue;
            }

            if (placedInfected < initialInfected) {
                agents[y][x] = new InfectedHost(y, x);
                placedInfected++;
            } else if (placedVaccinated < initialVaccinated) {
                agents[y][x] = new VaccinatedHost(y, x);
                placedVaccinated++;
            } else {
                agents[y][x] = new HealthyHost(y, x);
                placedHealthy++;
            }
        }
    }

    // Called from MainFrame to register how each state looks
    public static void registerStates(GridPanel panel) {
        panel.registerState(EMPTY, Color.lightGray);  // background for empty

        panel.registerState(HEALTHY,   Color.white, Color.green,     "H");
        panel.registerState(INFECTED,  Color.white, Color.red,       "I");
        panel.registerState(RECOVERED, Color.white, Color.blue,      "R");
        panel.registerState(VACCINATED,Color.black, Color.yellow,    "V");
        panel.registerState(DEAD,      Color.white, Color.darkGray,  "X");
    }
}