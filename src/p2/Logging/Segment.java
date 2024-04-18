package p2.Logging;

import p2.Enums.Light;
import p2.Enums.ObjectType;
import p2.Events.Event;
import p2.Events.LightEvent;
import p2.Events.OccupiedEvent;

/**
 * Represents a segment of a route in the train management system.
 * A segment is a section of a route between two stations.
 * Each segment has a traffic light that controls the flow of trains.
 */
public class Segment extends AbstractEntity implements Comparable<Segment> {
    private final Station segmentStart; // The start station of this segment
    private final Station segmentEnd; // The end station of this segment
    private TrafficLight trafficLight = new TrafficLight(); // The traffic light associated with this segment
    private Train currentTrain; // The train currently in this segment
    private final ObjectType type = ObjectType.Segment_; // The type of this object

    // Constructor

    /**
     * Constructs a new Segment with the given name, start station, and end station.
     *
     * @param name  the name of the segment
     * @param start the name of the start station
     * @param sEnd  the name of the end station
     */
    public Segment(String name, String start, String sEnd) {
        super(name);
        segmentStart = new Station(start);
        segmentEnd = new Station(sEnd);
    }

    // Getter methods

    /**
     * This method is used to get the start station of a segment.
     *
     * @return Station This returns the start station of the segment.
     */
    public Station getSegmentStart() {
        return segmentStart;
    }

    /**
     * This method is used to get the end station of a segment.
     *
     * @return Station This returns the end station of the segment.
     */
    public Station getSegmentEnd() {
        return segmentEnd;
    }

    public TrafficLight getTrafficLight() {
        return this.trafficLight;
    }

    /**
     * This method is used to set the traffic light of the segment.
     *
     * @param trafficLight the traffic light of the segment
     */
    public void setTrafficLight(TrafficLight trafficLight) {
        this.trafficLight = trafficLight;
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

    // Setter methods

    public Train getCurrentTrain() {
        return currentTrain;
    }

    public void setCurrentTrain(String currentTrain) {
        this.currentTrain = getTrainSystem().getTrainByName(currentTrain);
    }

    // Methods related to traffic light

    /**
     * Changes the color of the traffic light associated with this segment.
     * It delegates the change operation to the TrafficLight object.
     */
    public Event changeLight(int time) {
        Light currentColor = trafficLight.getColour();
        trafficLight.change();
        Light toColor = (currentColor == Light.RED) ? Light.GREEN : Light.RED;
        return new LightEvent(this.getName(), time, currentColor, toColor);
    }

    /**
     * Retrieves the current color of the traffic light associated with this segment.
     *
     * @return Light enum value representing the current color of the traffic light.
     * It can be either Light.RED or Light.GREEN.
     */
    public Light lightColour() {
        return this.trafficLight.getColour();
    }

    // Methods related to train

    /**
     * Checks if the segment currently has a Train.
     *
     * @return true if the segment has a Train, false otherwise
     */
    public boolean hasTrain() {
        return currentTrain != null;
    }

    /**
     * Accepts a Train by setting the currentTrain to the given Train.
     * If the segment already has a Train, an IllegalStateException is thrown.
     *
     * @param train the Train to be accepted
     * @throws IllegalStateException if the segment already has a Train
     */
    public Event acceptTrain(Train train, int time) {
        if (!hasTrain() && isOpen() && trafficLight.isGreen()) setCurrentTrain(train.getName());
        else throw new IllegalStateException("Train already in segment.");
        return new OccupiedEvent(this.getName(), time, train.getName(), true);
    }

    /**
     * Releases the current Train.
     * If the segment does not have a Train, an IllegalStateException is thrown.
     *
     * @throws IllegalStateException if the segment does not have a Train
     */
    public Event releaseTrain(int time) {
        if (hasTrain() && segmentEnd.isOpen()) setCurrentTrain(null);
        else throw new IllegalStateException("No train in segment.");
        return new OccupiedEvent(this.getName(), time, this.currentTrain.getName(), false);
    }

    // Verification methods

    /**
     * Verifies the status of the segment and its associated entities.
     * This method checks the verification status of the parent entity, the traffic light, the start station, and the end station.
     * It also checks that the start and end stations are not the same and that they are both open, and the segment itself.
     *
     * @return true if all verifications are successful and the segment is open, false otherwise
     */
    @Override
    public boolean verify() {
        return super.verify() && trafficLight.verify() && segmentStart.verify() && segmentEnd.verify() && segmentStart
                != segmentEnd && segmentStart.isOpen() && segmentEnd.isOpen() && this.isOpen();
    }

    @Override
    public boolean validate() {
        return false;
    }

    // toString method

    /**
     * Returns a string representation of the Segment.
     *
     * @return a string representation of the Segment in the format "Segment [name=NAME, segmentStart=START, segmentEnd=END, status=STATUS, trafficLight=LIGHT, train=TRAIN, verified=VERIFIED]"
     * where NAME is the name of the segment,
     * START is the name of the start station of the segment (or "none" if there is no start station),
     * END is the name of the end station of the segment (or "none" if there is no end station),
     * STATUS is the status of the segment,
     * LIGHT is the status of the traffic light of the segment,
     * TRAIN is the name of the train currently in the segment (or "none" if there is no train),
     * VERIFIED is "Yes" if the segment passes verification, "No" otherwise.
     */
    @Override
    public String toString() {
        return "Segment [name=" + getName() + ", segmentStart=" + (segmentStart == null ? "none" : segmentStart.getName())
                + ", segmentEnd=" + (segmentEnd == null ? "none" : segmentEnd.getName()) + ", status="
                + getStatus().getDescription() + ", trafficLight=" + trafficLight + ", train="
                + (getCurrentTrain() == null ? "none" : getCurrentTrain()) + ", verified=" + (verify() ? "Yes" : "No") + "]";
    }

    // Comparable method

    /**
     * Compares this Segment to another Segment based on the concatenated names of their start and end stations.
     * This method is used for sorting purposes.
     *
     * @param segment the Segment to be compared
     * @return a negative integer, zero, or a positive integer as this Segment is less than, equal to, or greater than the specified Segment
     */
    @Override
    public int compareTo(Segment segment) {
        String s = segmentStart.getName().concat(segmentEnd.getName()),
                e = segment.segmentStart.getName().concat(segment.segmentEnd.getName());
        return s.compareTo(e);
    }
}