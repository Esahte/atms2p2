package p2.Logging;

import p2.Enums.Action;
import p2.Enums.ObjectType;
import p2.Enums.RSStatus;
import p2.Events.CFOSEvent;
import p2.Events.Event;
import p2.Interfaces.IsVerifiable;
import p2.TrainSystem;

/**
 * AbstractEntity is an abstract class that implements the IsVerifiable interface.
 * It represents an entity that can be verified and can accept and release a Train.
 * The entity has a name and a status.
 */
abstract class AbstractEntity extends Logable implements IsVerifiable {
    // The name of the entity
    private final String name;
    // The status of the entity
    private RSStatus status = RSStatus.Open;
    private final TrainSystem trainSystem = new TrainSystem();
    private int currentTime;

    /**
     * Constructs a new AbstractEntity with the given name.
     * <p>
     * This constructor initializes a new AbstractEntity with the provided name. The status of the entity is set to Open by default.
     *
     * @param name the name of the entity
     */
    public AbstractEntity(String name) {
        this.name = name;
    }

    /**
     * This method is used to set the current time of the object.
     *
     * @param currentTime This is the current time of the object.
     */
    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    /**
     * This method is used to get the current time of the object.
     *
     * @return int This returns the current time of the object.
     */
    public int getCurrentTime() {
        return currentTime;
    }

    /**
     * This method is used to get the type of the object.
     *
     * @return ObjectType This returns the type of the object.
     */
    public abstract ObjectType getType();

    /**
     * This method is used to get the train system of the object.
     *
     * @return TrainSystem This returns the train system of the object.
     */
    public TrainSystem getTrainSystem() {
        return trainSystem;
    }

    /**
     * This method is used to get the type of the object.
     *
     * @return ObjectType This returns the type of the object.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Checks if the entity is open.
     *
     * @return true if the entity is open, false otherwise
     */
    public boolean isOpen() {
        return this.getStatus() == RSStatus.Open;
    }

    /**
     * Verifies the entity by checking if its name is not null and not an empty string.
     * This method is required by the IsVerifiable interface.
     *
     * @return true if the name is not null and not an empty string, false otherwise
     */
    @Override
    public boolean verify() {
        return this.name != null && !this.name.isEmpty();
    }

    /**
     * Closes the entity by setting its status to ClosedForMaintenance.
     * This method can be used when the entity needs to be temporarily unavailable for operations.
     *
     * @return Event - an instance of CFOSEvent indicating the entity has been closed
     */
    public Event close() {
        status = RSStatus.ClosedForMaintenance;
        return new CFOSEvent(this.getName(), getCurrentTime(), Action.Close);
    }

    /**
     * Opens the entity by setting its status to Open.
     * This method can be used when the entity is ready to be available for operations.
     *
     * @return Event - an instance of CFOSEvent indicating the entity has been opened
     */
    public Event open() {
        status = RSStatus.Open;
        return new CFOSEvent(this.getName(), getCurrentTime(), Action.Open);
    }

    /**
     * Retrieves the name of the entity.
     * <p>
     * This method returns the name of the entity as a string. The name is a unique identifier for the entity.
     *
     * @return the name of the entity
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the status of the entity.
     * <p>
     * This method returns the current status of the entity. The status indicates whether the entity is open, closed, or in a different state.
     *
     * @return the status of the entity
     */
    public RSStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the entity.
     * <p>
     * This method sets the status of the entity to the provided status. The status indicates whether the entity is open, closed, or in a different state.
     *
     * @param status the new status of the entity
     */
    public void setStatus(RSStatus status) {
        this.status = status;
    }
}