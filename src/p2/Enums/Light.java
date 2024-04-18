package p2.Enums;

/**
 * Enum representing the status of a Traffic Light.
 */
public enum Light {
    /**
     * State representing that the traffic light is red.
     */
    RED("Light is Red"),

    /**
     * State representing that the traffic light is green.
     */
    GREEN("Light is Green");

    /**
     * Description of the light.
     */
    private final String description;

    /**
     * Constructs a new Light with the given description.
     *
     * @param description the description of the light
     */
    Light(String description) {
        this.description = description;
    }

    /**
     * Returns the description of the light.
     *
     * @return the description of the light
     */
    public String getDescription() {
        return description;
    }
}

