package p2.Logging;

import p2.Enums.ObjectType;
import p2.Events.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.IntStream;

/**
 * Represents a route in a train system.
 * A route is a sequence of stations and segments, and can be either a round trip or a one-way trip.
 */
public class Route extends AbstractEntity implements Comparable<Route> {
    private final boolean isRoundTrip;
    private final ArrayList<Station> stations = new ArrayList<>();
    private final ArrayList<Segment> segments;
    private final ObjectType type = ObjectType.Route_;

    /**
     * Constructs a new Route with the given name, round trip status, and list of segments.
     *
     * @param name        the name of the route
     * @param isRoundTrip whether the route is a round trip
     * @param segments    the list of segments on the route
     */
    public Route(String name, boolean isRoundTrip, ArrayList<Segment> segments) {
        super(name);
        this.isRoundTrip = isRoundTrip;
        this.segments = segments;
    }

    // Getter methods

    /**
     * Returns whether the route is a round trip.
     *
     * @return true if the route is a round trip, false otherwise
     */
    public boolean isRoundTrip() {
        return this.isRoundTrip;
    }

    /**
     * Returns the start station of the route.
     *
     * @return the start station of the route
     */
    public Station getStart() {
        return stations.get(0);
    }

    /**
     * Returns the end station of the route.
     *
     * @return the end station of the route
     */
    public Station getEnd() {
        return stations.get(stations.size() - 1);
    }

    /**
     * Returns the list of stations on the route.
     *
     * @return the list of stations on the route
     */
    public ArrayList<Segment> getSegmentList() {
        return this.segments;
    }

    /**
     * Returns the list of stations on the route.
     *
     * @return the list of stations on the route
     */
    public ArrayList<Station> getStationList() {
        return this.stations;
    }

    /**
     * Returns the object type of the route.
     *
     * @return the object type of the route
     */
    @Override
    public ObjectType getType() {
        return type;
    }

    // Methods related to stations

    /**
     * Returns the next station after the given station on the route.
     *
     * @param station the station to find the next station of
     * @return the next station after the given station on the route, or null if there is no next station
     */
    public Station getNextStation(String station) {
        return getStationList().stream().dropWhile(station1 -> !station1.getName().equals(station)).skip(1).findFirst().orElse(null);
    }

    /**
     * Returns the previous station before the given station on the route.
     *
     * @param station   the station to find the previous station of
     * @param isAtStart whether the given station is at the start of the route
     * @return the previous station before the given station on the route, or null if there is no previous station
     */
    public Station getPreviousStation(String station, boolean isAtStart) {
        if (isAtStart) {
            return getStart();
        } else {
            ArrayList<Station> reversedStations = new ArrayList<>(getStationList());
            Collections.reverse(reversedStations);
            return reversedStations.stream().dropWhile(station1 -> !station1.getName().equals(station)).skip(1).findFirst().orElse(null);
        }
    }

    /**
     * Returns whether it is possible to get to the given station on the route.
     *
     * @param station the station to check
     * @return true if it is possible to get to the given station on the route, false otherwise
     */
    public boolean canGetTo(String station) {
        return stations.stream().filter(station1 -> station1.getName().equals(station)).allMatch(Station::isOpen);
    }

    // Methods related to segments

    /**
     * Adds a segment to the route.
     *
     * @param segment the segment to add
     */
    public void addSegment(Segment segment) {
        segments.add(segment);
    }

    /**
     * Adds a list of segments to the route.
     *
     * @param stationArrayList the list of segments to add
     */
    public void addStations(ArrayList<Station> stationArrayList) {
        stations.clear();
        stations.addAll(stationArrayList);
    }

    /**
     * Removes a segment from the route.
     *
     * @param segment the segment to remove
     */
    public void removeSegment(String segment) {
        segments.removeIf(segment1 -> segment1.getName().equals(segment));
    }

    /**
     * Returns whether the route contains a segment.
     *
     * @param segment the segment to check
     * @return true if the route contains the segment, false otherwise
     */
    public boolean containsSegment(String segment) {
        return segments.stream().anyMatch(segment1 -> segment1.getName().equals(segment));
    }

    /**
     * Changes the light at the start of a segment on the route.
     *
     * @param startOfSegment the start of the segment to change the light of
     * @return an event representing the light change, or null if there is no such segment
     */
    public Event changeLight(String startOfSegment) {
        return this.segments.stream().filter(segment -> segment.getName().equals(startOfSegment))
                .map(segment -> segment.changeLight(getCurrentTime()))
                .findFirst().orElse(null);
    }

    /**
     * Returns whether the segments on the route are properly sequenced.
     *
     * @return true if the segments on the route are properly sequenced, false otherwise
     */
    public boolean areSegmentsProperlySequenced() {
        return IntStream.range(0, segments.size() - 1)
                .allMatch(i -> segments.get(i).getSegmentEnd().equals(segments.get(i + 1).getSegmentStart()));
    }

    /**
     * Returns the next segment from the current station name.
     *
     * @param station the current station name
     * @return the next segment from the current station name
     */
    public Segment getNextSegment(String station) {
        return segments.stream().filter(segment -> segment.getSegmentStart().getName().equals(station)).findFirst().orElse(null);
    }

    // Verification methods

    /**
     * Verifies the route.
     *
     * @return true if the route is valid, false otherwise
     */
    @Override
    public boolean verify() {
        return true;
    }

    /**
     * Validates the route.
     *
     * @return false, as this method is not implemented
     */
    @Override
    public boolean validate() {
        return false;
    }

    // toString method

    /**
     * Returns a string representation of the route.
     *
     * @return a string representation of the route
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");
        for (Segment s : segments) str.append(s.getName()).append(segments.get(segments.size() - 1) == s ? "]" : ", ");

        return "Route [name=" + getName() + ", isRoundTrip=" + isRoundTrip + ", status=" + getStatus() + ", segments="
                + (segments.isEmpty() ? "none" : str.toString()) + ", verified=" + (verify() ? "Yes" : "No")
                + "]";
    }

    // compareTo method

    /**
     * Compares this route with another route.
     *
     * @param route the route to compare this route to
     * @return a negative integer, zero, or a positive integer if this route is less than, equal to, or greater than the other route
     */
    @Override
    public int compareTo(Route route) {
        return getName().compareTo(route.getName());
    }
}