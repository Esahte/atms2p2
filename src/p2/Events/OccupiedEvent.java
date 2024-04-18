package p2.Events;

/**
 * OccupiedEvent is a class that extends the Event class. It represents a specific type of event
 * that involves a train occupying a station.
 */
public class OccupiedEvent extends Event {
    // The train involved in this event
    private final String train;
    // Whether the train is entering or exiting the station
    private final boolean isEntry;

    /**
     * Constructs a new OccupiedEvent with the specified object name, time, train, and entry status.
     *
     * @param objectName the name of the object associated with this event
     * @param time       the time at which this event occurs
     * @param train      the train involved in this event
     * @param isEntry    whether the train is entering or exiting the station
     */
    public OccupiedEvent(String objectName, int time, String train, boolean isEntry) {
        super(objectName, time);
        this.train = train;
        this.isEntry = isEntry;
    }

    /**
     * Checks if the provided object is equal to this OccupiedEvent.
     * Two OccupiedEvents are considered equal if they have the same object name, time, train, and entry status.
     *
     * @param event the object to be compared for equality with this OccupiedEvent
     * @return true if the provided object is equal to this OccupiedEvent, false otherwise
     */
    @Override
    public boolean equals(Object event) {
        if (event instanceof OccupiedEvent)
            return super.equals(event) && train.equals(((OccupiedEvent) event).train)
                    && isEntry == ((OccupiedEvent) event).isEntry;
        return false;
    }

    /**
     * Returns a string representation of the OccupiedEvent.
     * The string representation is in the format "Enter Station Event [Object=[objectName], Time()=[time], Train=[train]]" if the train is entering the station,
     * or "Left Station Event [Object=[objectName], Time()=[time], Train=[train]]" if the train is leaving the station.
     *
     * @return a string representation of the OccupiedEvent
     */
    @Override
    public String toString() {
        return (isEntry ? "Enter Station Event" : "Left Station Event") + "[" + super.toString() + ", Train="
                + train + "]";
    }
}