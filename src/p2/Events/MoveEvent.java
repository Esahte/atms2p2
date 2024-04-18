package p2.Events;

/**
 * MoveEvent is a class that extends the Event class. It represents a specific type of event
 * that involves a movement from one station to another.
 */
public class MoveEvent extends Event {
    // The station from which the object is moving
    private final String fromStation;
    // The station to which the object is moving
    private final String toStation;

    /**
     * Constructs a new MoveEvent with the specified object name, time, and station details.
     *
     * @param objectName  the name of the object associated with this event
     * @param time        the time at which this event occurs
     * @param fromStation the station from which the object is moving
     * @param toStation   the station to which the object is moving
     */
    public MoveEvent(String objectName, int time, String fromStation, String toStation) {
        super(objectName, time);
        this.fromStation = fromStation;
        this.toStation = toStation;
    }

    /**
     * Returns a string representation of the MoveEvent.
     * The string representation is in the format "MoveEvent [Object=[objectName], Time()=[time], From Station=[fromStation], To Station=[toStation]]".
     *
     * @return a string representation of the MoveEvent
     */
    @Override
    public String toString() {
        return "MoveEvent [" + super.toString() + ", From Station=" + fromStation + ", To Station=" + toStation + "]";
    }

    /**
     * Checks if the provided object is equal to this MoveEvent.
     * Two MoveEvents are considered equal if they have the same object name, time, and station details (both fromStation and toStation).
     *
     * @param event the object to be compared for equality with this MoveEvent
     * @return true if the provided object is equal to this MoveEvent, false otherwise
     */
    @Override
    public boolean equals(Object event) {
        if (event instanceof MoveEvent)
            return super.equals(event) && fromStation.equals(((MoveEvent) event).fromStation)
                    && toStation.equals(((MoveEvent) event).toStation);
        return false;
    }
}