package p2.Logging;

import p2.Enums.ObjectType;

/**
 * Station is a class that extends DocEntity.
 * It represents a station in a railway system.
 */
public class Station extends AbstractEntity implements Comparable<Station> {
    private final ObjectType type = ObjectType.Station_; // The type of this object

    /**
     * Constructs a new Station with the given name.
     * <p>
     * This constructor initializes a new Station by calling the superclass constructor with the provided name.
     * The Station represents a stop or destination in a railway system.
     *
     * @param name the name of the station
     */
    public Station(String name) {
        super(name);
    }

    /**
     * This method is used to get the type of the object.
     *
     * @return ObjectType This returns the type of the object.
     */
    @Override
    public ObjectType getType() {
        return type;
    }

    // Verification methods

    /**
     * Verifies the status of the station.
     * This method checks the verification status of the parent entity and the station itself.
     *
     * @return true if all verifications are successful and the station is open, false otherwise
     */
    @Override
    public boolean verify() {
        return super.verify() && this.isOpen();
    }

    /**
     * Validates the station.
     * This method checks if the station is valid.
     *
     * @return true if the station is valid, false otherwise
     */
    @Override
    public boolean validate() {
        return false;
    }

    // toString method

    /**
     * Returns a string representation of the Station.
     *
     * @return a string representation of the Station in the format "Station [name=NAME, status=STATUS]"
     * where NAME is the name of the station,
     * STATUS is the status of the station.
     */
    @Override
    public String toString() {
        return "Station [name=" + getName() + ", status=" + getStatus() + "]";
    }

    // Comparable method

    /**
     * Compares this Station to another Station based on their names.
     * This method is used for sorting purposes.
     *
     * @param station the Station to be compared
     * @return a negative integer, zero, or a positive integer as this Station is less than, equal to, or greater than the specified Station
     */
    @Override
    public int compareTo(Station station) {
        return getName().compareTo(station.getName());
    }
}