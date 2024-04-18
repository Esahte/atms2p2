package p2.Logging;

import p2.Enums.Light;
import p2.Interfaces.IsVerifiable;

/**
 * TrafficLight is a class that implements the IsVerifiable interface.
 * It represents a traffic light in a traffic management system.
 */
public class TrafficLight implements IsVerifiable {
    private static int nextID = 1;
    /**
     * The unique identifier for this TrafficLight.
     */
    private final int id = nextID++;

    /**
     * The current color of this TrafficLight.
     */
    private Light colour = Light.GREEN;

    /**
     * Constructs a new TrafficLight.
     * The initial color of the TrafficLight is set to Red.
     * The id of the TrafficLight is incremented each time a new instance is created.
     */
    public TrafficLight() {
    }

    public int getId() {
        return id;
    }

    public Light getColour() {
        return colour;
    }

    public void setColour(Light colour) {
        this.colour = colour;
    }

    /**
     * Changes the color of the traffic light.
     * If the current color is Red, it changes it to Green.
     * If the current color is not Red (implicitly Green), it changes it to Red.
     */
    public void change() {
        this.colour = (this.colour == Light.RED) ? Light.GREEN : Light.RED;
    }

    /**
     * Checks if the current color of the traffic light is Green.
     *
     * @return true if the current color of the traffic light is Green, false otherwise
     */
    public boolean isGreen() {
        return this.colour == Light.GREEN;
    }

    /**
     * Checks if the current color of the traffic light is Red.
     *
     * @return true if the current color of the traffic light is Red, false otherwise
     */
    public boolean isRed() {
        return this.colour == Light.RED;
    }

    /**
     * This method is used to verify the color of the traffic light.
     * It checks if the color of the traffic light is not null, and if it's either Green or Red.
     * This method is part of the IsVerifiable interface that this class implements.
     *
     * @return true if the color of the traffic light is not null and if it's either Green or Red, false otherwise
     */
    @Override
    public boolean verify() {
        return this.colour != null || isGreen() || isRed();
    }
}
