package p2.Enums;

/**
 * The SimulatorStatus enum represents the various states a simulator can be in.
 * It is used throughout the application to manage and track the status of the simulator.
 */
public enum SimulatorStatus {
    /**
     * The Initialised status indicates that the simulator has been set up and is ready to start.
     */
    Initialised("Simulator is Initialised"),

    /**
     * The Finished status indicates that the simulator has completed its operation.
     */
    Finished("Simulator is Finished"),

    /**
     * The Uninitialised status indicates that the simulator is not yet set up.
     */
    Uninitialised("Simulator is Uninitialised"),

    /**
     * The Working status indicates that the simulator is currently in operation.
     */
    Working("Simulator is Working"),;

    /**
     * The description of the simulator status.
     */
    private final String description;

    /**
     * Constructs a new SimulatorStatus with the given description.
     *
     * @param description the description of the simulator status
     */
    SimulatorStatus(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the simulator status.
     *
     * @return the description of the simulator status
     */
    public String getDescription() {
        return description;
    }
}