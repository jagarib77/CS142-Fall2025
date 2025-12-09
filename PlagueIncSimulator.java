package jwp;

import java.awt.Color;
import java.util.Random;

/**
 * Controls the Plague Inc–style simulation.
 * Manages a grid of PlagueIncAgent objects, updates them each tick
 * (movement, infection spread, recovery, death, cure progress), and writes
 * state codes into the int[][] grid used by GridPanel for drawing.
 */
public class PlagueIncSimulator implements GridSimulator {

    // Cell state codes for GridPanel
    public static final int EMPTY      = 0;
    public static final int HEALTHY    = 1;
    public static final int INFECTED   = 2;
    public static final int RECOVERED  = 3;
    public static final int VACCINATED = 4;
    public static final int DEAD       = 5;

    /**
     * Encapsulates disease parameters like infectivity, lethality,
     * movement, duration and detectability.
     *
     * Having this as a class makes it easy to support both presets and
     * user-defined custom diseases.
     */
    public static class DiseaseProfile {
        private final String name;
        private final double infectionProbability;
        private final double deathProbability;
        private final double movementProbability;
        private final int minInfectionDuration;
        private final int maxInfectionDuration;
        private final double detectability;  // how visible this disease is to the world

        public DiseaseProfile(String name,
                              double infectionProbability,
                              double deathProbability,
                              double movementProbability,
                              int minInfectionDuration,
                              int maxInfectionDuration,
                              double detectability) {
            this.name = name;
            this.infectionProbability = infectionProbability;
            this.deathProbability = deathProbability;
            this.movementProbability = movementProbability;
            this.minInfectionDuration = minInfectionDuration;
            this.maxInfectionDuration = maxInfectionDuration;
            this.detectability = detectability;
        }

        public String getName() {
            return name;
        }

        public double getInfectionProbability() {
            return infectionProbability;
        }

        public double getDeathProbability() {
            return deathProbability;
        }

        public double getMovementProbability() {
            return movementProbability;
        }

        public int getMinInfectionDuration() {
            return minInfectionDuration;
        }

        public int getMaxInfectionDuration() {
            return maxInfectionDuration;
        }

        /**
         * How easy this disease is to notice globally (0.0 = invisible, 1.0 = very obvious).
         * Used to drive cure speed: more detectable diseases get cured faster.
         */
        public double getDetectability() {
            return detectability;
        }
    }

    // A few preset disease profiles you can choose from
    public static final DiseaseProfile PROFILE_MILD_FLU =
            new DiseaseProfile("Mild Flu",
                    0.90,  // infectionProbability
                    0.02,  // deathProbability
                    0.80,  // movementProbability
                    5,     // minInfectionDuration
                    15,    // maxInfectionDuration
                    0.20   // detectability (barely noticeable)
            );

    public static final DiseaseProfile PROFILE_DEADLY_VIRUS =
            new DiseaseProfile("Black Plague",
                    0.65,
                    0.85,
                    0.70,
                    10,
                    30,
                    0.70   // fairly visible
            );

    public static final DiseaseProfile PROFILE_RAPID_SPREAD =
            new DiseaseProfile("Ebola",
                    0.70,
                    0.90,
                    0.40,
                    15,
                    40,
                    0.90   // extremely obvious
            );

    // Simulation parameters that are not disease-specific
    // (These control how many people we spawn and what fractions start infected/vaccinated.)
    private double density               = 0.40;  // fraction of grid initially occupied
    private double initialInfectedFrac   = 0.02;  // fraction of population starting infected
    private double initialVaccinatedFrac = 0.10;  // fraction of population starting vaccinated

    // Current disease profile (defaults to deadly virus)
    private DiseaseProfile diseaseProfile = PROFILE_DEADLY_VIRUS;

    // Main simulation grid (actual agents)
    private PlagueIncAgent[][] agents;
    private final Random rand = new Random();

    // Bookkeeping for stats / endgame checks
    private int tickCounter = 0;
    private boolean gameOver = false;
    private String endMessage = "";

    private int lastHealthyCount;
    private int lastInfectedCount;
    private int lastRecoveredCount;
    private int lastVaccinatedCount;
    private int lastDeadCount;

    // Cure-related fields
    // cureProgress goes from 0.0 (no cure) to 1.0 (full cure discovered).
    private double cureProgress = 0.0;
    private final double baseCureRate = 0.01;       // slow background cure research
    private final double cureVisibilityFactor = 0.15; // scales with visible cases & detectability

    @Override
    public void tick(int[][] states, int width, int height) {
        if (states == null) {
            return;
        }

        // (Re)initialize agent grid if needed
        if (agents == null || agents.length != height || agents[0].length != width) {
            initAgents(width, height);
            gameOver = false;
            endMessage = "";
            tickCounter = 0;
            cureProgress = 0.0;
        }

        // If the game is over, keep showing the final state but don't update the world
        if (gameOver) {
            copyAgentsToStates(states, width, height);
            return;
        }

        tickCounter++;

        DiseaseProfile profile = this.diseaseProfile;

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
                if (agent.isAlive() && rand.nextDouble() < profile.getMovementProbability()) {
                    int dir = rand.nextInt(4); // 0=right,1=left,2=down,3=up
                    int nx = x;
                    int ny = y;
                    switch (dir) {
                        case 0 -> nx = x + 1;
                        case 1 -> nx = x - 1;
                        case 2 -> ny = y + 1;
                        case 3 -> ny = y - 1;
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
        // Infected hosts are main spreaders; recovered hosts spread at a lower rate.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                PlagueIncAgent agent = agents[y][x];
                if (agent instanceof HostAgent host) {
                    // We explicitly allow both infected and recovered to spread.
                    if (agent instanceof InfectedHost || agent instanceof RecoveredHost) {
                        infectNeighbor(x + 1, y, width, height, host);
                        infectNeighbor(x - 1, y, width, height, host);
                        infectNeighbor(x, y + 1, width, height, host);
                        infectNeighbor(x, y - 1, width, height, host);
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

                    if (age >= profile.getMinInfectionDuration()) {
                        double resolveChance =
                                (double) (age - profile.getMinInfectionDuration() + 1)
                                        / (profile.getMaxInfectionDuration()
                                        - profile.getMinInfectionDuration() + 1);
                        if (resolveChance > 1.0) {
                            resolveChance = 1.0;
                        }

                        if (rand.nextDouble() < resolveChance) {
                            // infection ends this tick: decide recover vs die
                            if (rand.nextDouble() < profile.getDeathProbability()) {
                                agents[y][x] = new DeadHost(y, x);
                            } else {
                                agents[y][x] = new RecoveredHost(y, x);
                            }
                        }
                    }
                }
            }
        }

        // 4. Update stats, cure progress, check for endgame, and copy to drawing grid
        updateCountsAndCheckEndgame(width, height);
        updateCureProgress();
        copyAgentsToStates(states, width, height);
    }

    /**
     * Compute the effective infection probability after factoring in
     * global cure progress. As cureProgress approaches 1.0, infection
     * probability shrinks towards 0.
     */
    private double getEffectiveInfectionProbability() {
        double base = diseaseProfile.getInfectionProbability();
        double cureFactor = 1.0 - cureProgress; // 1.0 = no cure, 0.0 = full cure

        if (cureFactor < 0.0) {
            cureFactor = 0.0;
        }

        return base * cureFactor;
    }

    /**
     * Infects a neighboring host with a probability that depends on:
     * - base infectionProbability (scaled by cure progress)
     * - source host type (infected vs recovered = strong vs weak spreader)
     * - target host type (vaccinated vs healthy = hard vs easy to infect)
     */
    private void infectNeighbor(int x, int y, int width, int height, HostAgent source) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return;
        }

        PlagueIncAgent neighbor = agents[y][x];
        if (!(neighbor instanceof HostAgent target)) {
            return;
        }

        // Determine susceptibility based on target type
        double susceptibilityMultiplier;

        if (target instanceof DeadHost) {
            // Dead hosts cannot be infected again.
            return;
        } else if (target instanceof RecoveredHost) {
            // Recovered hosts are immune in this model.
            return;
        } else if (target instanceof VaccinatedHost) {
            // Vaccinated hosts are harder to infect, but not impossible.
            susceptibilityMultiplier = 0.20; // 20% as likely as a healthy host
        } else if (target instanceof HealthyHost) {
            // Fully susceptible.
            susceptibilityMultiplier = 1.0;
        } else {
            // Fallback: use canBeInfected() if we don't know the specific type.
            if (!target.canBeInfected()) {
                return;
            }
            susceptibilityMultiplier = 1.0;
        }

        // Determine infectivity based on source type
        double infectivityMultiplier;
        if (source instanceof InfectedHost) {
            infectivityMultiplier = 1.0;  // main spreaders
        } else if (source instanceof RecoveredHost) {
            infectivityMultiplier = 0.3;  // weaker spreaders
        } else {
            infectivityMultiplier = 0.0;  // others do not spread
        }

        if (infectivityMultiplier <= 0.0 || susceptibilityMultiplier <= 0.0) {
            return;
        }

        double base = getEffectiveInfectionProbability();
        double p = base * infectivityMultiplier * susceptibilityMultiplier;

        // Clamp to [0,1] just to be safe
        if (p <= 0.0) {
            return;
        }
        if (p > 1.0) {
            p = 1.0;
        }

        if (rand.nextDouble() < p) {
            agents[y][x] = new InfectedHost(y, x);
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

    // Copy agents grid into the int[][] states for GridPanel to draw
    private void copyAgentsToStates(int[][] states, int width, int height) {
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

    /**
     * Count how many of each state we have and determine if the game is over.
     * (Humanity wins vs plague wins.)
     */
    private void updateCountsAndCheckEndgame(int width, int height) {
        int healthy = 0;
        int infected = 0;
        int recovered = 0;
        int vaccinated = 0;
        int dead = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                PlagueIncAgent agent = agents[y][x];
                if (agent == null) {
                    continue;
                }
                switch (agent.getStateCode()) {
                    case HEALTHY -> healthy++;
                    case INFECTED -> infected++;
                    case RECOVERED -> recovered++;
                    case VACCINATED -> vaccinated++;
                    case DEAD -> dead++;
                }
            }
        }

        lastHealthyCount = healthy;
        lastInfectedCount = infected;
        lastRecoveredCount = recovered;
        lastVaccinatedCount = vaccinated;
        lastDeadCount = dead;

        if (!gameOver) {
            // Humanity wins: no infected, and at least one survivor (healthy, vaccinated, or recovered)
            if (infected == 0 && (healthy + vaccinated + recovered) > 0) {
                gameOver = true;
                endMessage = "Plague eradicated! Some hosts survived.";
                System.out.println(endMessage);
            }
            // Plague wins: no infected and no survivors, at least one agent and all dead
            else if (infected == 0 && healthy == 0 && vaccinated == 0 && recovered == 0 && dead > 0) {
                gameOver = true;
                endMessage = "Everyone died. The plague has wiped out all hosts.";
                System.out.println(endMessage);
            }
        }
    }

    /**
     * Advances cureProgress based on:
     * - a small base cure rate
     * - how visible the disease is (infected + dead fraction)
     * - the disease's detectability parameter
     */
    private void updateCureProgress() {
        int totalHosts = lastHealthyCount + lastInfectedCount
                + lastRecoveredCount + lastVaccinatedCount
                + lastDeadCount;

        if (totalHosts <= 0) {
            return;
        }

        // Fraction of population that is clearly "visible" to the world
        double visibleFraction = (double) (lastInfectedCount + lastDeadCount) / totalHosts;

        // Cure advances faster if the disease is more detectable and more visible
        double increment = baseCureRate
                + diseaseProfile.getDetectability()
                * visibleFraction
                * cureVisibilityFactor;

        cureProgress += increment;
        if (cureProgress > 1.0) {
            cureProgress = 1.0;
        }
    }

    // Use one of the predefined disease profiles
    public void setDiseaseProfile(DiseaseProfile profile) {
        if (profile != null) {
            this.diseaseProfile = profile;
        }
    }

    // Custom disease: lets the caller set infectivity, lethality, etc.
    // For backwards compatibility, detectability is set to a default (0.5).
    public void setCustomDisease(String name,
                                 double infectionProbability,
                                 double deathProbability,
                                 double movementProbability,
                                 int minInfectionDuration,
                                 int maxInfectionDuration) {
        this.diseaseProfile = new DiseaseProfile(
                name,
                infectionProbability,
                deathProbability,
                movementProbability,
                minInfectionDuration,
                maxInfectionDuration,
                0.5   // default detectability for custom disease
        );
    }

    // Optional: extended custom disease that also specifies detectability.
    public void setCustomDisease(String name,
                                 double infectionProbability,
                                 double deathProbability,
                                 double movementProbability,
                                 int minInfectionDuration,
                                 int maxInfectionDuration,
                                 double detectability) {
        this.diseaseProfile = new DiseaseProfile(
                name,
                infectionProbability,
                deathProbability,
                movementProbability,
                minInfectionDuration,
                maxInfectionDuration,
                detectability
        );
    }

    // Getters for stats / meta info (useful for GUI if you want)
    public DiseaseProfile getDiseaseProfile() {
        return diseaseProfile;
    }

    public String getDiseaseName() {
        return diseaseProfile.getName();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getEndMessage() {
        return endMessage;
    }

    public int getTickCounter() {
        return tickCounter;
    }

    public int getHealthyCount() {
        return lastHealthyCount;
    }

    public int getInfectedCount() {
        return lastInfectedCount;
    }

    public int getRecoveredCount() {
        return lastRecoveredCount;
    }

    public int getVaccinatedCount() {
        return lastVaccinatedCount;
    }

    public int getDeadCount() {
        return lastDeadCount;
    }
    public double getCureProgress() {
        return cureProgress;
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