package p2;

import p2.Enums.ObjectType;
import p2.Enums.SystemStatus;
import p2.Enums.TrainStatus;
import p2.Events.Event;
import p2.Interfaces.IsVerifiable;
import p2.Logging.Route;
import p2.Logging.Segment;
import p2.Logging.Station;
import p2.Logging.Train;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The TrainSystem class represents a train system in a transportation system.
 * It implements IsVerifiable and IsMovable interfaces.
 * A TrainSystem has a status, a list of stations, segments, routes, and trains.
 */
public class TrainSystem implements IsVerifiable {
    // Represents a list of stations in the train system
    private final ArrayList<Station> stations = new ArrayList<>();
    // Represents a list of segments in the train system
    private final ArrayList<Segment> segments = new ArrayList<>();
    // Represents a list of routes in the train system
    private final ArrayList<Route> routes = new ArrayList<>();
    // Represents a list of trains in the train system
    private final ArrayList<Train> trains = new ArrayList<>();
    // Represents the status of the train system
    private SystemStatus status = SystemStatus.Initialised;
    // Create a system time
    private int currentTime = 0;

    /**
     * Constructs a new TrainSystem with the initial status.
     */
    public TrainSystem() {
    }

    /**
     * Returns the string representation of the train system.
     *
     * @return the string representation of the train system
     */
    @Override
    public String toString() {
        StringBuilder sts = new StringBuilder("[");
        if (stations.isEmpty())
            sts.append("none]");
        else { // extract helper method for this
            Station[] acc1 = stations.toArray(new Station[0]);
            Arrays.sort(acc1);
            List<Station> ss = Arrays.asList(acc1);
            for (Station s : ss)
                sts.append(ss.indexOf(s) == 0 ? "\n\t" : "\t").append(s).append(ss.indexOf(s) != ss.size() - 1 ? "\n" : "\n\t]");
        }

        StringBuilder sgs = new StringBuilder("[");
        if (segments.isEmpty())
            sgs.append("none]");
        else {
            Segment[] acc1 = segments.toArray(new Segment[0]);
            Arrays.sort(acc1);
            List<Segment> ss = Arrays.asList(acc1);
            for (Segment s : ss)
                sgs.append(ss.indexOf(s) == 0 ? "\n\t" : "\t").append(s).append(ss.indexOf(s) != ss.size() - 1 ? "\n" : "\n\t]");
        }

        StringBuilder rts = new StringBuilder("[");
        if (routes.isEmpty())
            rts.append("none]");
        else {
            Route[] acc1 = routes.toArray(new Route[0]);
            Arrays.sort(acc1);
            List<Route> ss = Arrays.asList(acc1);
            for (Route s : ss)
                rts.append(ss.indexOf(s) == 0 ? "\n\t" : "\t").append(s).append(ss.indexOf(s) != ss.size() - 1 ? "\n" : "\n\t]");
        }

        StringBuilder tns = new StringBuilder("[");
        if (trains.isEmpty())
            tns.append("none]");
        else {
            Train[] acc1 = trains.toArray(new Train[0]);
            Arrays.sort(acc1);
            List<Train> ss = Arrays.asList(acc1);
            for (Train s : trains)
                tns.append(ss.indexOf(s) == 0 ? "\n\t" : "\t").append(s).append(ss.indexOf(s) != ss.size() - 1 ? "\n" : "\n\t]");
        }
        return "---------- ---------- ---------- ---------- ---------- ----------\nTrainSystem [\n\nstatus="
                + status.getDescription() + "\nverified=" + (verify() ? "Yes" : "No") + "\n\ntrains=" + tns
                + "\n\nroutes=" + rts + "\n\nsegments=" + sgs + "\n\nstations=" + sts
                + "\n]\n---------- ---------- ---------- ---------- ---------- ----------";
    }

    /**
     * Returns the current time of the train system.
     *
     * @return the current time of the train system
     */
    public int getCurrentTime() {
        return currentTime;
    }

    /**
     * Sets the current time of the train system.
     */
    public void incrementTime() {
        this.currentTime++;
        stations.forEach(station -> station.setCurrentTime(currentTime));
        segments.forEach(segment -> segment.setCurrentTime(currentTime));
        routes.forEach(route -> route.setCurrentTime(currentTime));
        trains.forEach(train -> train.setCurrentTime(currentTime));
    }

    /**
     * Returns the list of stations in the train system.
     *
     * @return the list of stations
     */
    public ArrayList<Station> getStations() {
        return stations;
    }

    /**
     * Returns the list of segments in the train system.
     *
     * @return the list of segments
     */
    public ArrayList<Segment> getSegments() {
        return segments;
    }

    /**
     * Returns the list of routes in the train system.
     *
     * @return the list of routes
     */
    public ArrayList<Route> getRoutes() {
        return routes;
    }

    /**
     * Returns the list of trains in the train system.
     *
     * @return the list of trains
     */
    public ArrayList<Train> getTrains() {
        return trains;
    }

    /**
     * Adds a new station to the train system.
     * The system must be in the Initialised state.
     *
     * @param sName the name of the station to be added
     * @throws IllegalStateException if the system is not in the Initialised state
     */
    public void addStation(String sName) {
        if (status != SystemStatus.Initialised) {
            throw new IllegalStateException("System is not in the Initialised state.");
        }
        this.stations.add(new Station(sName));
    }

    /**
     * Removes a station from the train system.
     * The system must be in the Initialised state.
     *
     * @param sName the name of the station to be removed
     * @throws IllegalStateException if the system is not in the Initialised state
     */
    public void removeStation(String sName) {
        if (status != SystemStatus.Initialised) {
            throw new IllegalStateException("System is not in the Initialised state.");
        }
        stations.removeIf(station -> station.getName().equals(sName));
    }

    /**
     * Opens a station in the train system.
     *
     * @param sName the name of the station to be opened
     */
    public Event openStation(String sName) {
        Station station = getStationByName(sName);
        return (station.verify()) ? station.open() : null;
    }

    /**
     * Closes a station in the train system.
     *
     * @param sName the name of the station to be closed
     */
    public Event closeStation(String sName) {
        return getStationByName(sName).close();
    }

    /**
     * Adds a new segment to the train system.
     * The system must be in the Initialised state.
     *
     * @param sName the name of the segment to be added
     * @param start the start of the segment
     * @param sEnd  the end of the segment
     * @throws IllegalStateException if the system is not in the Initialised state
     */
    public void addSegment(String sName, String start, String sEnd) {
        if (status != SystemStatus.Initialised) {
            throw new IllegalStateException("System is not in the Initialised state.");
        }
        segments.add(new Segment(sName, start, sEnd));
    }

    /**
     * Removes a segment from the train system.
     * The system must be in the Initialised state.
     *
     * @param sName the name of the segment to be removed
     * @throws IllegalStateException if the system is not in the Initialised state
     */
    public void removeSegment(String sName) {
        if (status != SystemStatus.Initialised) {
            throw new IllegalStateException("System is not in the Initialised state.");
        }
        segments.removeIf(segment -> segment.getName().equals(sName));
    }

    /**
     * Opens a segment in the train system.
     *
     * @param sName the name of the segment to be opened
     */
    public Event openSegment(String sName) {
        Segment segment = getSegmentByName(sName);
        return (segment.verify() && segment.getSegmentStart().isOpen() && segment.getSegmentEnd().isOpen()
                && segment.getTrafficLight().isGreen()) ? segment.open() : null;
    }

    /**
     * Closes a segment in the train system.
     *
     * @param sName the name of the segment to be closed
     */
    public Event closeSegment(String sName) {
        return getSegmentByName(sName).close();
    }

    /**
     * Adds a new route to the train system.
     * The system must be in the Initialised state.
     *
     * @param rName       the name of the route to be added
     * @param isRoundTrip the round trip status of the route
     * @param rSegments   the segment of the route
     * @throws IllegalStateException if the system is not in the Initialised state
     */
    public void addRoute(String rName, boolean isRoundTrip, String[] rSegments) {
        if (status != SystemStatus.Initialised) {
            throw new IllegalStateException("System is not in the Initialised state.");
        }

        // First, convert the segment names to Segment objects, checking for nulls
        ArrayList<Segment> segments = Arrays.stream(rSegments)
                .map(this::getSegmentByName)
                .peek(segment -> {
                    if (segment == null) {
                        throw new IllegalArgumentException("One of the segments is not found.");
                    }
                })
                .collect(Collectors.toCollection(ArrayList::new));

        // Create the route with the segments
        Route route = new Route(rName, isRoundTrip, segments);

        // Extract stations from segments
        Set<Station> stations = segments.stream()
                .flatMap(segment -> Stream.of(segment.getSegmentStart(), segment.getSegmentEnd()))
                .collect(Collectors.toSet());

        // Add stations to the route
        route.addStations(new ArrayList<>(stations));
        routes.add(route);
    }


    /**
     * Removes a route from the train system.
     * The system must be in the Initialised state.
     *
     * @param rName the name of the route to be removed
     * @throws IllegalStateException if the system is not in the Initialised state
     */
    public void removeRoute(String rName) {
        if (status != SystemStatus.Initialised) {
            throw new IllegalStateException("System is not in the Initialised state.");
        }
        routes.removeIf(route -> route.getName().equals(rName));
    }

    /**
     * Opens a route in the train system.
     *
     * @param rName the name of the route to be opened
     */
    public Event openRoute(String rName) {
        return (getRouteByName(rName).verify()) ? getRouteByName(rName).open() : null;
    }

    /**
     * Closes a route in the train system.
     *
     * @param rName the name of the route to be closed
     */
    public Event closeRoute(String rName) {
        return getRouteByName(rName).close();
    }

    /**
     * Adds a new train to the train system.
     * The system must be in the Initialised state.
     *
     * @throws IllegalStateException if the system is not in the Initialised state
     */
    public void addTrain(String name, int startTime) {
        if (status != SystemStatus.Initialised) {
            throw new IllegalStateException("System is not in the Initialised state.");
        }
        trains.add(new Train(name, startTime));
    }

    /**
     * Removes a train from the train system.
     * The system must be in the Initialised state.
     *
     * @param id the id of the train to be removed
     * @throws IllegalStateException if the system is not in the Initialised state
     */
    public void removeTrain(int id) {
        if (status != SystemStatus.Initialised) {
            throw new IllegalStateException("System is not in the Initialised state.");
        }
        trains.removeIf(train -> train.getId() == id);
    }

    /**
     * Registers a train to a route in the train system.
     *
     * @param trainName the id of the train to be registered
     * @param routeName the name of the route to be registered to
     * @param stations  the stops of the train
     */
    public void registerTrain(String trainName, String routeName, String[] stations) {
        ArrayList<String> stops = Arrays.stream(stations).collect(Collectors.toCollection(ArrayList::new));
        // Find the train with the provided ID
        Train train = this.trains.stream().filter(train1 -> train1.getName().equals(trainName)).findFirst().orElse(null);
        // Find the route with the provided name
        Route route = this.routes.stream().filter(route1 -> route1.getName().equals(routeName)).findFirst().orElse(null);

        // If the train and route are found, and the train is not yet registered, and the route is verified and open
        if (train != null && route != null && !train.isRegistered() && route.verify() && route.isOpen()) {
            // Register the train to the route with the provided start time
            train.setCurrentRoute(route);
            train.setDesignatedStops(stops);
            train.register(getCurrentTime());
        }
    }

    /**
     * De-registers a train from a route in the train system.
     *
     * @param trainName the id of the train to be deregistered
     */
    public void deRegisterTrain(String trainName) {
        Train train = this.trains.stream().filter(train1 -> train1.getName().equals(trainName)).findFirst().orElse(null);

        if (train != null && train.isRegistered()) {
            train.deregister();
        }
    }

    /**
     * Checks if the train system contains a given station.
     *
     * @param station the name of the station
     * @return true if the train system contains the station, false otherwise
     */
    public boolean containsStation(String station) {
        return stations.stream().anyMatch(station1 -> station1.getName().equals(station));
    }

    /**
     * Checks if the train system contains a given segment.
     *
     * @param segment the name of the segment
     * @return true if the train system contains the segment, false otherwise
     */
    public boolean containsSegment(String segment) {
        return segments.stream().anyMatch(segment1 -> segment1.getName().equals(segment));
    }

    /**
     * Checks if the train system contains a given route.
     *
     * @param route the name of the route
     * @return true if the train system contains the route, false otherwise
     */
    public boolean containsRoute(String route) {
        return routes.stream().anyMatch(route1 -> route1.getName().equals(route));
    }

    /**
     * Checks if the train system contains a given train.
     *
     * @param train the id of the train
     * @return true if the train system contains the train, false otherwise
     */
    public boolean containsTrain(int train) {
        return trains.stream().anyMatch(train1 -> train1.getId() == train);
    }

    /**
     * Returns the information of a given station in the train system.
     *
     * @param station the name of the station
     * @return the information of the station
     */
    public String getStationInfo(String station) {
        return stations.stream().filter(station1 -> station1.getName().equals(station)).findFirst().toString();
    }

    /**
     * Returns the information of a given segment in the train system.
     *
     * @param segment the name of the segment
     * @return the information of the segment
     */
    public String getSegmentInfo(String segment) {
        return segments.stream().filter(segment1 -> segment1.getName().equals(segment)).findFirst().toString();
    }

    /**
     * Returns the information of a given route in the train system.
     *
     * @param route the name of the route
     * @return the information of the route
     */
    public String getRouteInfo(String route) {
        return routes.stream().filter(route1 -> route1.getName().equals(route)).findFirst().toString();
    }

    /**
     * Returns the information of a given train in the train system.
     *
     * @param train the id of the train
     * @return the information of the train
     */
    public String getTrainInfo(int train) {
        return trains.stream().filter(train1 -> train1.getId() == train).findFirst().toString();
    }

    /**
     * Sets the status of the train system to working.
     */
    public void setToWorking() {
        this.status = SystemStatus.Operational;
    }

    /**
     * Sets the status of the train system to stop.
     */
    public void setStopped() {
        this.status = SystemStatus.Finished;
    }

    /**
     * Returns the current status of the train system.
     *
     * @return the current status of the train system
     */
    public SystemStatus currentStatus() {
        return this.status;
    }

    /**
     * Verifies the train system.
     *
     * @return true if the train system is valid, false otherwise
     */
    @Override
    public boolean verify() {
        boolean selfVerify = stations.stream().allMatch(Station::verify) &&
                segments.stream().allMatch(Segment::verify) &&
                routes.stream().allMatch(Route::verify) &&
                trains.stream().allMatch(Train::verify);
        boolean duplicates = stations.stream().distinct().count() == stations.size() &&
                segments.stream().distinct().count() == segments.size() &&
                routes.stream().distinct().count() == routes.size() &&
                trains.stream().distinct().count() == trains.size();
        return selfVerify && duplicates;
    }

    /**
     * Checks if the closure is hindering the movement of the train.
     *
     * @return true if the closure is hindering the movement of the train, false otherwise
     */
    public boolean closureHinderingMovement() {
        /*
         * call this method if advance returns no events, and you want to determine if
         * the system is deadlocked, i.e., there are trains to move, but none of them can
         * move because of closures.
         *
         * this method should not return true if trains have stopped at a station and
         * are waiting on a future time instant to move.
         *
         */
        return false;
    }

    /**
     * Advances the time in the train system.
     *
     * @return the advanced time
     */
    public List<Event> advance() {
        if (status != SystemStatus.Operational) {
            throw new IllegalStateException("The system is not operational.");
        }

        List<Event> events = new ArrayList<>();
        // Iterate over all trains
        for (Train train : trains) {
            // Set the trains waiting time
            train.setWaitTimeRemaining();
            if (train.isRegistered()) {
                // Check if the train is at the start, it's not waiting, and it's not started
                if (train.isAtStart() && !train.isWaiting() && train.getStatus() != TrainStatus.Started) {
                    // Start the train
                    events.add(train.start());
                    // Open route
                    events.add(openRoute(train.getCurrentRoute().getName()));
                } else if (train.getCurrentRoute().getEnd().getName().strip().equals(train.currentStation().strip())) { // Check if the train is at the end, and its end time is the current time
                    // Finish the train
                    events.add(train.finish());
                    // Deregister the train
                    deRegisterTrain(train.getName());
                }
            // Check the status of the train
            checkTrainStatus(train, events);
            }
        }
        return events;
    }

    /**
     * Checks the status of the train.
     *
     * @param train  The train to check the status.
     * @param events A list of events that occur during the simulation.
     */
    private void checkTrainStatus(Train train, List<Event> events) {
        Station currentStation = getStationByName(train.currentStation());
        Station nextStation = getStationByName(train.nextStation());
        // Check if the train is started, the current station is open, the next station is verified, and there are no closures hindering the movement
        if (train.getStatus() == TrainStatus.Started) {
            if (nextStation.verify() && currentStation.isOpen()) {
                // Process entering and exiting the segments
                processSegmentTransition(train, events);
            } else {
                // If the next station is not verified, reset the train's wait time
                train.resetWaitTimeRemaining();
            }
        }
    }

    /**
     * This method handles the transition of a train through a segment in the train system.
     * It checks the current state of the segment and the train and performs actions accordingly.
     *
     * @param train  The train that is transitioning through the segment.
     * @param events A list of events that occur during the simulation.
     */
    private void processSegmentTransition(Train train, List<Event> events) {
        Segment currentSegment = train.getCurrentSegment();
        // If the current segment has a train, and it is not open, open the segment and release the train
        if (currentSegment.hasTrain() && !currentSegment.isOpen()) {
            openSegmentAndReleaseTrain(currentSegment, train, currentTime, events);
        } else if (!currentSegment.hasTrain() && !currentSegment.isOpen()) { // If the current segment does not have a train, and it is not open, reset the trains wait time
            train.resetWaitTimeRemaining();
        }

        // If the train's current station is a designated stop, remove it from the list of designated stops and reset the train's wait time
        if (train.getDesignatedStops().contains(train.currentStation())) {
            train.getDesignatedStops().remove(train.currentStation());
            train.resetWaitTimeRemaining();
        }

        // If the current segment does not have a train, it is open, and the train is not waiting, accept the train into the segment
        if (!currentSegment.hasTrain() && currentSegment.isOpen() && !train.isWaiting()) {
            acceptTrainIntoSegment(currentSegment, train, currentTime, events);
        }
    }

    /**
     * Opens a segment and releases a train from it.
     * If the traffic light of the segment is red, it changes the light.
     * Then, it releases the train from the segment and advances the train.
     *
     * @param currentSegment The segment to be opened.
     * @param train          The train to be released.
     * @param currentTime    The current time in the simulation.
     * @param events         A list of events that occur during the simulation.
     */
    private void openSegmentAndReleaseTrain(Segment currentSegment, Train train, int currentTime, List<Event> events) {
        events.add(currentSegment.open());
        if (currentSegment.getTrafficLight().isRed()) {
            events.add(currentSegment.changeLight(currentTime));
        }
        events.add(currentSegment.releaseTrain(currentTime));
        events.add(train.advance(currentTime));
    }

    /**
     * Accepts a train into a segment.
     * It adds the event of the train being accepted into the segment to the list of events.
     * Then, it closes the segment.
     * If the traffic light of the segment is green, it changes the light.
     *
     * @param currentSegment The segment to accept the train.
     * @param train          The train to be accepted.
     * @param currentTime    The current time in the simulation.
     * @param events         A list of events that occur during the simulation.
     */
    private void acceptTrainIntoSegment(Segment currentSegment, Train train, int currentTime, List<Event> events) {
        events.add(currentSegment.acceptTrain(train, currentTime));
        events.add(currentSegment.close());
        if (currentSegment.getTrafficLight().isGreen()) {
            events.add(currentSegment.changeLight(currentTime));
        }
    }

    /**
     * Returns the segment with the given name.
     *
     * @param segmentName The name of the segment.
     * @return The segment with the given name, or null if no such segment exists.
     */
    public Segment getSegmentByName(String segmentName) {
        return segments.stream().filter(segment -> segment.getName().strip().equals(segmentName.strip())).findFirst().orElse(null);
    }

    /**
     * Returns the station with the given name.
     *
     * @param stationName The name of the station.
     * @return The station with the given name, or null if no such station exists.
     */
    public Station getStationByName(String stationName) {
        return stations.stream().filter(station -> station.getName().strip().equals(stationName.strip())).findFirst().orElse(null);
    }

    /**
     * Returns the route with the given name.
     *
     * @param routeName The name of the route.
     * @return The route with the given name, or null if no such route exists.
     */
    public Route getRouteByName(String routeName) {
        return routes.stream().filter(route -> route.getName().strip().equals(routeName.strip())).findFirst().orElse(null);
    }

    /**
     * Returns the train with the given name.
     *
     * @param trainName The name of the train.
     * @return The train with the given name, or null if no such train exists.
     */
    public Train getTrainByName(String trainName) {
        return trains.stream().filter(train -> train.getName().strip().equals(trainName.strip())).findFirst().orElse(null);
    }

    public boolean validateObjectLog(ObjectType object, String name, ArrayList<String> events) {
        return true;
    }
}