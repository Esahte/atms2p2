package p2.Enums;

/**
 * Enum representing the status of a Railway Station (RS).
 */
public enum RSStatus {
    /**
     * State representing that the railway station is open.
     */
    Open("Open"),

    /**
     * State representing that the railway station is closed for maintenance.
     */
    ClosedForMaintenance("Closed for Maintenance");

    /**
     * Description of the railway station status.
     */
    private final String description;

    /**
     * Constructor for the enum.
     *
     * @param description A string describing the status.
     */
    RSStatus(String description) {
        this.description = description;
    }

    /**
     * Method to get the description of the status.
     *
     * @return A string representing the description of the status.
     */
    public String getDescription() {
        return description;
    }
}
