package p2.Events;

import p2.Enums.Light;

/**
 * LightEvent is a class that extends the Event class. It represents a specific type of event
 * that involves a change in light color.
 */
public class LightEvent extends Event {
    // The initial color of the light before the event
    private final Light fromColour;
    // The final color of the light after the event
    private final Light toColour;

    /**
     * Constructs a new LightEvent with the specified object name, time, and light colors.
     *
     * @param objectName the name of the object associated with this event
     * @param time       the time at which this event occurs
     * @param fromColour the initial color of the light before the event
     * @param toColour   the final color of the light after the event
     */
    public LightEvent(String objectName, int time, Light fromColour, Light toColour) {
        super(objectName, time);
        this.fromColour = fromColour;
        this.toColour = toColour;
    }

    /**
     * Returns a string representation of the LightEvent.
     * The string representation is in the format "LightEvent [Object=[objectName], Time()=[time], From colour=[fromColour], To colour=[toColour]]".
     *
     * @return a string representation of the LightEvent
     */
    @Override
    public String toString() {
        return "LightEvent [" + super.toString() + ", From colour=" + fromColour.getDescription() + ", To colour="
                + toColour.getDescription() + "]";
    }

    /**
     * Checks if the provided object is equal to this LightEvent.
     * Two LightEvents are considered equal if they have the same object name, time, and light colors (both fromColour and toColour).
     *
     * @param event the object to be compared for equality with this LightEvent
     * @return true if the provided object is equal to this LightEvent, false otherwise
     */
    @Override
    public boolean equals(Object event) {
        if (event instanceof LightEvent)
            return super.equals(event) && fromColour == ((LightEvent) event).fromColour
                    && toColour == ((LightEvent) event).toColour;
        return false;
    }
}