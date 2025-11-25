App controls running the simulation
MainFrame controls what's being run by App
GridPanel contains the simulator/grid logic
randomMovement is an example state for reference

## Authors and Contributions

- **Lukas McAteer**
  - Created initial GUI framework (`App.java`, `MainFrame.java`, `GridPanel.java`)
  - Set up `GridSimulator` interface and original demo simulator
  - Integrated the grid with Swing timer and window setup

- **Landon Carothers**
  - Designed and implemented Plague Inc–style simulation logic
  - Created behavior/inheritance classes:
    `PlagueIncAgent.java`, `HostAgent.java`,
    `HealthyHost.java`, `InfectedHost.java`,
    `RecoveredHost.java`, `VaccinatedHost.java`, `DeadHost.java`
  - Implemented `PlagueIncSimulator.java` and updated `MainFrame.java`
    to use the new simulation
