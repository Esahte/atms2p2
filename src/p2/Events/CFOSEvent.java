package p2.Events;

import p2.Enums.Action;

/**
 * CFOSEvent is a class that extends the Event class. It represents a specific type of event
 * that contains an action.
 */
public class CFOSEvent extends Event {
    // The action associated with this event
    private final Action action;

    /**
     * Constructs a new CFOSEvent with the specified object name, time, and action.
     *
     * @param objectName the name of the object associated with this event
     * @param time       the time at which this event occurs
     * @param action     the action associated with this event
     */
    public CFOSEvent(String objectName, int time, Action action) {
        super(objectName, time);
        this.action = action;
    }

    /**
     * Checks if the provided object is equal to this CFOSEvent.
     * Two CFOSEvents are considered equal if they have the same object name, time, and action.
     *
     * @param event the object to be compared for equality with this CFOSEvent
     * @return true if the provided object is equal to this CFOSEvent, false otherwise
     */
    @Override
    public boolean equals(Object event) {
        if (event instanceof CFOSEvent)
            return super.equals(event) && action == ((CFOSEvent) event).action;
        return false;
    }

    /**
     * Returns a string representation of the CFOSEvent.
     * The string representation is in the format "Action Event [Object=[objectName], Time()=[time]]".
     *
     * @return a string representation of the CFOSEvent
     */
    @Override
    public String toString() {
        return action + " Event [" + super.toString() + "]";
    }
}
