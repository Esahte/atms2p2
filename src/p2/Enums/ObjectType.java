package p2.Enums;

/**
 * Enum representing different types of objects in the simulation.
 * Each enum value is associated with a description.
 */
public enum ObjectType {
    /**
     * Represents a Route object.
     */
    Route_("Route"),       // Represents a Route object

    /**
     * Represents a Segment object.
     */
    Segment_("Segment"),   // Represents a Segment object

    /**
     * Represents a Station object.
     */
    Station_("Station"),   // Represents a Station object

    /**
     * Represents a Train object.
     */
    Train_("Train"),       // Represents a Train object

    /**
     * Represents a TrafficLight object.
     */
    TrafficLight_("Train"); // Represents a TrafficLight object

    /**
     * Description of the object type.
     */
    private final String description; // Description of the object type

    /**
     * Constructor for the ObjectType enum.
     * @param description The description of the object type.
     */
    ObjectType(String description) {
        this.description = description;
    }

    /**
     * Get the description of the object type.
     * @return The description of the object type.
     */
    public String getDescription() {
        return description;
    }
}
