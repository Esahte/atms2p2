package p2.Logging;

import p2.Enums.Action;
import p2.Enums.ObjectType;
import p2.Enums.TrainStatus;
import p2.Events.CFOSEvent;
import p2.Events.Event;
import p2.Events.MoveEvent;
import p2.Interfaces.IsVerifiable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The Train class represents a train in a transportation system.
 * It implements IsVerifiable and IsMovable interfaces.
 * A Train has an id, time registered, start time, current location, current route, and designated stops.
 */
public class Train extends Logable implements IsVerifiable {
    // Represents the next id of the train
    private static int nextID = 1;
    // Represent the Object Type of the Train
    private final ObjectType type = ObjectType.Train_;
    // Represents the id of the train
    private final int id = nextID++;
    // Represents the name of the train
    private final String name;
    // Represent the current time
    private int currentTime;
    // Represents the time the train was registered
    private int timeRegistered = 0;
    // Represents the start time of the train
    private int startTime = 0;
    // Represents the current location of the train
    private String currentLocation;
    // Represents the current station of the train
    private Station currentStation;
    // Represents the current segment of the train
    private Segment currentSegment;
    // Represents the current route of the train
    private Route currentRoute;
    // Represents if the train is at the start of the route
    private boolean isAtStart;
    // Represents the wait time remaining for the train
    private int waitTimeRemaining = 0;
    // Represents the designated stops of the train
    private ArrayList<String> designatedStops = new ArrayList<>();
    // Represents the status of the train
    private TrainStatus status = TrainStatus.Initialised;

    /**
     * Constructs a new Train with the given name and start time.
     * If the name is empty, the train's name will be "Train" followed by its id.
     * If the start time is negative, the train's start time will be set to its current start time.
     * The train's status is set to initialize.
     * The current time is retrieved from the train system's simulator.
     *
     * @param name      the name of the train
     * @param startTime the start time of the train
     */
    public Train(String name, int startTime) {
        this.name = name.isEmpty() ? "Train " + id : name;
        this.startTime = startTime >= 0 ? startTime : this.startTime;
    }

    // Getter methods

    /**
     * Returns the id of the train.
     *
     * @return the id of the train
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the train.
     *
     * @return the name of the train
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the start time of the train.
     *
     * @return the start time of the train
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Returns the current route of the train.
     *
     * @return the current route of the train
     */
    public Route getCurrentRoute() {
        return currentRoute;
    }

    /**
     * Sets the current route of the train.
     *
     * @param currentRoute the current route of the train
     */
    public void setCurrentRoute(Route currentRoute) {
        this.currentRoute = currentRoute;
    }

    /**
     * Returns the designated stops of the train.
     *
     * @return the designated stops of the train
     */
    public ArrayList<String> getDesignatedStops() {
        return designatedStops;
    }

    /**
     * Sets the designated stops of the train.
     *
     * @param designatedStops the designated stops of the train
     */
    public void setDesignatedStops(ArrayList<String> designatedStops) {
        this.designatedStops = designatedStops;
    }

    /**
     * Checks if the train is registered.
     *
     * @return true if the train is registered, false otherwise
     */
    public boolean isRegistered() {
        return this.timeRegistered > 0;
    }

    /**
     * Returns the time the train was registered.
     *
     * @return the time the train was registered
     */
    public int whenRegistered() {
        return this.timeRegistered;
    }

    /**
     * Returns the current station of the train.
     *
     * @return the current station of the train
     */
    public String currentStation() {
        return currentStation.getName();
    }

    /**
     * Returns the next station of the train.
     *
     * @return the next station of the train
     */
    public String nextStation() {
        return getCurrentRoute().getNextStation(currentStation()).getName();
    }

    /**
     * Returns the current segment of the train.
     *
     * @return the current segment of the train
     */
    public Segment getCurrentSegment() {
        return currentSegment;
    }

    /**
     * Returns the object type of the train.
     *
     * @return the object type of the train
     */
    public ObjectType getType() {
        return type;
    }

    /**
     * This method is used to get the current time of the object.
     *
     * @return int This returns the current time of the object.
     */
    public int getCurrentTime() {
        return currentTime;
    }

    // Setter methods

    /**
     * Returns the status of the train.
     *
     * @return the status of the train
     */
    public TrainStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the train.
     *
     * @param status the status of the train
     */
    public void setStatus(TrainStatus status) {
        this.status = status;
    }

    /**
     * Checks if the train is at the start of the route.
     *
     * @return true if the train is at the start of the route, false otherwise
     */
    public boolean isAtStart() {
        return isAtStart;
    }

    /**
     * Sets the current location of the train.
     *
     * @param currentLocation the current location of the train
     */
    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    // create method to set waiting time remaining it does not have a parameter
    /**
     * This method is used to set the wait time remaining for the train.
     */
    public void setWaitTimeRemaining() {
        this.waitTimeRemaining = (startTime + whenRegistered()) - getCurrentTime();
    }

    /**
     * Resets the wait time remaining for the train.
     */
    public void resetWaitTimeRemaining() {
        this.waitTimeRemaining = getCurrentTime() + 1;
    }

    /**
     * This method is used to set the current time of the object.
     *
     * @param currentTime This is the current time of the object.
     */
    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    // Methods related to train's actions

    /**
     * This method is used to register a train with a given start time and route.
     * If the route's station list is not empty, the train is registered to stop at all stations in the route.
     * After successful registration, the waitTimeRemaining is set to the start time.
     *
     * @param time The start time of the train. This is an integer value representing the time the train is scheduled to start.
     */
    public void register(int time) {
        // Set the start time of the train
        this.timeRegistered = time;
        // Set the current route of the train
        this.currentStation = currentRoute.getStart();
        // Set the current segment of the train
        this.currentSegment = currentRoute.getSegmentList().stream().filter(segment -> segment.getSegmentStart().getName().strip().equals(currentStation.getName().strip())).findFirst().orElse(null);
        // Set the current location of the train
        this.currentLocation = currentStation.getName();
        // isAtStart is set to true
        this.isAtStart = Objects.equals(currentLocation, currentRoute.getStart().getName());
        // Set the wait time remaining for the train to the start time
        setWaitTimeRemaining();
    }

    /**
     * De-registers the train by resetting the start time, current route, current location, and designated stops.
     */
    public void deregister() {
        startTime = -1;
        changeRout(null);
        setCurrentLocation(null);
        setDesignatedStops(null);
    }

    /**
     * Checks if the train is waiting.
     *
     * @return true if the train is waiting, false otherwise
     */
    public boolean isWaiting() {
        return this.waitTimeRemaining > 0;
    }

    /**
     * Starts the train if it is valid and sets its status to start.
     *
     * @return an event representing the start of the train
     */
    public Event start() {
        if (validate()) setStatus(TrainStatus.Started);
        return new CFOSEvent(getName(), getCurrentTime(), Action.Start);
    }

    /**
     * Finishes the train if its current location is the end of the route and sets its status to complete.
     *
     * @return an event representing the finish of the train
     */
    public Event finish() {
        if (currentLocation.equals(currentRoute.getEnd().getName())) setStatus(TrainStatus.Completed);
        return new CFOSEvent(getName(), getCurrentTime(), Action.Finish);
    }

    /**
     * Advances the time in the train.
     * If the train is at the start and has no designated stops, it is registered to stop at all stations in the route.
     * If the route is valid, the train's status is started, and it is possible to get to the next station, the train moves to the next station.
     *
     * @param time the time to be advanced
     * @return an event representing the movement of the train
     */
    public Event advance(int time) {
        if (currentRoute.verify() && status.equals(TrainStatus.Started) && currentRoute.canGetTo(nextStation())) {
            currentStation = currentRoute.getNextStation(currentStation());
        } else {
            System.out.print("There seems to be an issue with the route or the train status.");
        }

        return new MoveEvent(this.name, time, this.currentStation(), nextStation());
    }

    /**
     * Adds a stop to the designated stops of the train.
     *
     * @param stop the stop to add
     */
    public void addStop(String stop) {
        this.designatedStops.add(stop);
    }

    /**
     * Changes the route of the train.
     *
     * @param route the new route of the train
     */
    public void changeRout(Route route) {
        this.currentRoute = route;
    }

    // Verification methods

    /**
     * Verifies the train.
     *
     * @return true if the train is valid, false otherwise
     */
    @Override
    public boolean verify() {
        return getCurrentRoute().verify() && isRegistered();
    }

    /**
     * Validates the train.
     *
     * @return true if the train is valid, false otherwise
     */
    @Override
    public boolean validate() {
        return status.equals(TrainStatus.Initialised) && id != 0 && name != null && verify();
    }

    // toString method

    /**
     * Returns a string representation of the train.
     *
     * @return a string representation of the train
     */
    @Override
    public String toString() {
        return "Train [id=" + id + ", name=" + name + ", " + "timeRegistered="
                + (timeRegistered <= 0 ? "unregistered" : timeRegistered) + ", startTime="
                + (timeRegistered >= 0 ? getStartTime() : "unregistered") + ", currentStation="
                + (currentStation == null ? "none" : currentStation) + ", route="
                + (currentRoute == null ? "none" : currentRoute.getName()) + ", stopsAt="
                + (!designatedStops.isEmpty() ? designatedStops.toString() : "All") + ", status=" + status
                + ", verified=" + (verify() ? "Yes" : "No") + "]";
    }
}