package p2.Enums;

/**
 * Enum representing the various states a system can be in.
 */
public enum SystemStatus {
    /**
     * State representing that the system has been initialized.
     */
    Initialised("System is Initialised"),

    /**
     * State representing that the system is operational.
     */
    Operational("System is Operational"),

    /**
     * State representing that the system is deadlocked.
     */
    Deadlocked("System is Deadlocked"),

    /**
     * State representing that the system has finished its operations.
     */
    Finished("No More trains!");

    /**
     * Description of the system state.
     */
    private final String description;

    /**
     * Constructor for the enum.
     *
     * @param description A string describing the state.
     */
    SystemStatus(String description) {
        this.description = description;
    }

    /**
     * Method to get the description of the state.
     *
     * @return A string representing the description of the state.
     */
    public String getDescription() {
        return description;
    }
}