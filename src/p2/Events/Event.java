package p2.Events;

/**
 * Event is a class that represents a generic event with an object name and a time.
 * This class is used as a base class for other specific types of events.
 */
public abstract class Event {
    // The name of the object associated with this event
    private final String objectName;
    // The time at which this event occurs
    private final int time;

    /**
     * Constructs a new Event with the specified object name and time.
     *
     * @param objectName the name of the object associated with this event
     * @param time       the time at which this event occurs
     */
    public Event(String objectName, int time) {
        this.objectName = objectName;
        this.time = time;
    }

    /**
     * Returns the name of the object associated with this event.
     *
     * @return the name of the object associated with this event
     */
    public String getObjectName() {
        return objectName;
    }

    /**
     * Returns the time at which this event occurs.
     *
     * @return the time at which this event occurs
     */
    public int getTime() {
        return time;
    }

    /**
     * Checks if the provided object is equal to this event.
     * Two events are considered equal if they have the same object name and time.
     *
     * @param event the object to be compared for equality with this event
     * @return true if the provided object is equal to this event, false otherwise
     */
    @Override
    public boolean equals(Object event) {
        if (event instanceof Event)
            return objectName.equals(((Event) event).objectName) && time == (((Event) event).time);
        return false;
    }

    /**
     * Returns a string representation of the event.
     * The string representation is in the format "Object=[objectName], Time()=[time]".
     *
     * @return a string representation of the event
     */
    @Override
    public String toString() {
        return "Object=" + objectName + ", Time()=" + time;
    }
}