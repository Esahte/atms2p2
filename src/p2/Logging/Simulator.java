package p2.Logging;

import p2.Enums.SimulatorStatus;
import p2.Events.Event;
import p2.TrainSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Simulator extends Logable {
    private TrainSystem trainSystem = new TrainSystem();
    private SimulatorStatus status = SimulatorStatus.Uninitialised;
    private ArrayList<String> flaggedEvents = new ArrayList<>();
    private int nextTimeInstance = 0;
    private Scanner scanner;
    private String initialisationFile;

    public Simulator(String initialisationFile) throws FileNotFoundException {
        initialise(initialisationFile);
        status = SimulatorStatus.Initialised;
    }

    public Simulator(TrainSystem trainSystem) {
        status = SimulatorStatus.Initialised;
        this.trainSystem = trainSystem;
    }

    /**
     * Initialises the simulator by reading configuration from a file.
     *
     * @param initialisationFile The path to the initialization file.
     * @throws FileNotFoundException If the file is not found.
     */
    public void initialise(String initialisationFile) throws FileNotFoundException {
        if (!isInitialisationStatusValid()) {
            System.out.println("Initialisation status is not valid.");
            return;
        }

        File file = new File(initialisationFile);
        scanner = new Scanner(file);  // Assigning scanner at the class level
        processInitialisationFile(scanner, trainSystem.getCurrentTime());
    }

    /**
     * Checks if the simulator has a valid status for initialization.
     *
     * @return True if status is either Uninitialised or Initialised.
     */
    private boolean isInitialisationStatusValid() {
        return status == SimulatorStatus.Uninitialised || status == SimulatorStatus.Initialised;
    }

    /**
     * Processes the initialization file to set up the simulation environment.
     *
     * @throws NumberFormatException  If there is an issue with parsing numbers.
     * @throws NoSuchElementException If the expected input format is not met.
     */
    private void processInitialisationFile(Scanner scanner, int timeInstance) throws NumberFormatException, NoSuchElementException {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue; // Skip empty lines
            if (line.matches("^\\d+$")) { // Matches time instance lines
                nextTimeInstance = Integer.parseInt(line);
                if (nextTimeInstance != timeInstance) {
                    // Reached the next time instance, stop processing
                    return;
                }
            } else {
                // Process the line
                processConfigurationLine(line, scanner);
            }
        }
    }

    /**
     * Processes a single configuration line from the initialization file.
     *
     * @param line    The line to process.
     * @param scanner The scanner to read additional information from.
     */
    private void processConfigurationLine(String line, Scanner scanner) {
        String[] parts = line.split(":\\s*"); // Split on colon followed by any whitespace
        String key = parts[0];
        String value = (parts.length > 1) ? parts[1] : null;

        switch (key) {
            case "Stations" -> processStations(value, scanner);
            case "Segments" -> processSegments(value, scanner);
            case "Routes" -> processRoutes(value, scanner);
            case "Events" -> processEvents(value, scanner);
            case "Trains" -> processTrains(value, scanner);
            default -> {
                // Handle unknown keys or lines here if necessary
            }
        }
    }

    /**
     * Processes the 'Stations' part of the configuration file.
     *
     * @param value   The string containing the number of stations to be processed.
     * @param scanner The scanner to read the station information from.
     */
    private void processStations(String value, Scanner scanner) {
        int numberOfStations = Integer.parseInt(value);
        for (int i = 0; i < numberOfStations; i++) {
            String stationName = scanner.nextLine().trim();
            trainSystem.addStation(stationName.strip()); // Assumes addStation is a method in TrainSystem
        }
    }

    /**
     * Processes the 'Segments' part of the configuration file.
     *
     * @param value   The string containing the number of segments to be processed.
     * @param scanner The scanner to read the segment information from.
     */
    private void processSegments(String value, Scanner scanner) {
        int numberOfSegments = Integer.parseInt(value);
        for (int i = 0; i < numberOfSegments; i++) {
            String segmentInfo = scanner.nextLine().trim();
            String[] segmentParts = segmentInfo.split(":");
            trainSystem.addSegment(segmentParts[0].strip(), segmentParts[1].strip(), segmentParts[2].strip()); // Assumes addSegment is a method in TrainSystem
        }
    }

    /**
     * Processes the 'Routes' part of the configuration file.
     *
     * @param value   The string containing the number of routes to be processed.
     * @param scanner The scanner to read the route information from.
     */
    private void processRoutes(String value, Scanner scanner) {
        int numberOfRoutes = Integer.parseInt(value);
        for (int i = 0; i < numberOfRoutes; i++) {
            String routeInfo = scanner.nextLine().trim();
            String[] routeParts = routeInfo.split(":");
            boolean isRoundTrip = Boolean.parseBoolean(routeParts[1]);
            String[] segments = routeParts[2].split(";");
            trainSystem.addRoute(routeParts[0].strip(), isRoundTrip, segments); // Assumes addRoute is a method in TrainSystem
        }
    }

    /**
     * Processes the 'Events' part of the configuration file.
     *
     * @param value   The string containing the number of events to be processed.
     * @param scanner The scanner to read the event information from.
     */
    private void processEvents(String value, Scanner scanner) {
        int numberOfEvents = Integer.parseInt(value);
        for (int i = 0; i < numberOfEvents; i++) {
            String eventInfo = scanner.nextLine().trim();
            String[] eventParts = eventInfo.split(":");
            // Assumes methods for opening and closing stations, segments, and routes exist in TrainSystem
            if (eventParts[0].strip().equals("Open")) {
                processOpenEvent(eventParts);
            } else if (eventParts[0].strip().equals("Close")) {
                processCloseEvent(eventParts);
            }
        }
    }

    /**
     * Processes an 'Open' event for a station, segment, or route.
     *
     * @param eventParts The parts of the event information.
     * @throws IllegalArgumentException If the event type is unknown.
     */
    private void processOpenEvent(String[] eventParts) {
        switch (eventParts[1].strip()) {
            case "Station" -> addToLog(trainSystem.openStation(eventParts[2].strip()));
            case "Segment" -> addToLog(trainSystem.openSegment(eventParts[2].strip()));
            case "Route" -> addToLog(trainSystem.openRoute(eventParts[2].strip()));
            default -> throw new IllegalArgumentException("Unknown event type for 'Open': " + eventParts[1]);

        }
    }

    /**
     * Processes a 'Close' event for a station, segment, or route.
     *
     * @param eventParts The parts of the event information.
     * @throws IllegalArgumentException If the event type is unknown.
     */
    private void processCloseEvent(String[] eventParts) {
        switch (eventParts[1].strip()) {
            case "Station" -> addToLog(trainSystem.closeStation(eventParts[2].strip()));
            case "Segment" -> addToLog(trainSystem.closeSegment(eventParts[2].strip()));
            case "Route" -> addToLog(trainSystem.closeRoute(eventParts[2].strip()));
            default -> throw new IllegalArgumentException("Unknown event type for 'Close': " + eventParts[1]);
        }
    }

    /**
     * Processes the 'Trains' part of the configuration file.
     *
     * @param value   The string containing the number of trains to be processed.
     * @param scanner The scanner to read the train information from.
     */
    private void processTrains(String value, Scanner scanner) {
        int numberOfTrains = Integer.parseInt(value);
        for (int i = 0; i < numberOfTrains; i++) {
            String trainInfo = scanner.nextLine().trim();
            String[] trainParts = trainInfo.split(":");
            String[] stops = trainParts[3].split(";");
            // Check the sops if it is equal to an empty string or "all" register the train with an empty array
            if ((stops.length == 0 || stops[0].strip().equals("all")) && trainSystem.getCurrentTime() > 0) {
                trainSystem.registerTrain(trainParts[0].strip(), trainParts[2].strip(), new String[0]);
            } else if (trainSystem.getCurrentTime() > 0) {
                trainSystem.registerTrain(trainParts[0].strip(), trainParts[2].strip(), stops);
            } else if (trainSystem.getCurrentTime() == 0) {
                trainSystem.addTrain(trainParts[0].strip(), Integer.parseInt(trainParts[1]));
            }
        }
    }

    /**
     * Sets the train system.
     *
     * @param trainSystem the train system to set
     */
    public void system(TrainSystem trainSystem) {
        this.trainSystem = trainSystem;
    }

    /**
     * Validates the simulator's current state.
     *
     * @return true if the simulator's current state is valid, false otherwise
     */
    @Override
    public boolean validate() {
        // Ensure currentTime is not null and is non-negative
        return trainSystem.getCurrentTime() >= 0;
    }

    /**
     * Returns the status of the simulator.
     *
     * @return the status of the simulator
     */
    public SimulatorStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the simulator.
     *
     * @param status the status to set
     */
    public void setStatus(SimulatorStatus status) {
        this.status = status;
    }

    /**
     * Checks if the simulation is finished.
     *
     * @return true if the simulation is finished, false otherwise
     */
    public boolean isFinished() {
        return this.getStatus().equals(SimulatorStatus.Finished);
    }

    /**
     * Simulates the operation of the system.
     */
    public void simulate() throws FileNotFoundException {
        try {
            if (isFinished() || getStatus() != SimulatorStatus.Initialised) {
                throw new IllegalStateException("Simulation is either finished or not initialised.");
            }

            // Start the simulation
            setStatus(SimulatorStatus.Working);
            // Start the system
            trainSystem.setToWorking();
            while (getStatus() == SimulatorStatus.Working || scanner.hasNextLine()) {
                trainSystem.incrementTime();

                if (scanner.hasNextLine() && nextTimeInstance == trainSystem.getCurrentTime()) {
                    processInitialisationFile(scanner, trainSystem.getCurrentTime());
                }
                // tell the trainSystem to advance
                ArrayList<Event> events = (ArrayList<Event>) trainSystem.advance();

                /* process the events */
                for (Event e : events) {
                    if (e.getTime() != trainSystem.getCurrentTime())
                        flaggedEvents.add(e.toString());
                    addToLog(e);
//                    addToLog(e.toString());  // Print each event descriptively as it occurs
                    System.out.println(e.toString());
                }

                // Check if the system is deadlocked
                if (trainSystem.closureHinderingMovement()) trainSystem.setStopped();

                // Check if the system is finished
                if (trainSystem.isFinished()) setStatus(SimulatorStatus.Finished);
            }
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * Returns the current time.
     *
     * @return the current time
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(helperString(str.toString())).append("\n");
        str.append("--- Events --\n");
        str.append(logSize() == 0 ? " \tno events" : "");
        for (String object : getObjects()) {
            str.append("Object=[").append(object).append(", events=").append(logSize()).append("]\n");
            for (String event : getEvents(object))
                str.append("\t").append(event).append("\n");
        }
        str = new StringBuilder(helperString2(str.toString()));
        return str.toString();
    }

    /**
     * Returns a short string representation of the simulator.
     *
     * @return a short string representation of the simulator
     */
    public String toShortString() {
        String str = "";
        str = helperString(str);
        str += "There are " + logSize() + " events with " + distinctObjects() + " distinct objects.\n";
        str = helperString2(str);
        return str;
    }

    /**
     * Helper method to build the string representation of the simulator.
     *
     * @param str the string to build
     * @return the built string
     */
    private String helperString(String str) {
        str += "The current time instant is: " + trainSystem.getCurrentTime() + "\n";
        str += "The current status is: " + status.getDescription() + "\n";
        return str;
    }

    /**
     * Helper method to build the string representation of the simulator.
     *
     * @param str the string to build
     * @return the built string
     */
    private String helperString2(String str) {
        str += trainSystem.getCurrentTime() <= 0 ? "\nNothing to validate as yet."
                : "\n\nValidation has " + (validate() ? "passed" : "failed");
        return str;
    }

    /**
     * Operates the system by registering trains and adding stops.
     */
    public void operateSystem() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to register a train? (y/n)");
        String response = scanner.nextLine();

        while (response.equalsIgnoreCase("y")) {
            // Print all available trains
            System.out.println("Available trains:");
            for (Train train : trainSystem.getTrains()) {
                System.out.println(train.getName());
            }

            System.out.println("Enter the train name:");
            String trainName = scanner.nextLine();

            // Print all available routes
            System.out.println("Available routes:");
            for (Route route : trainSystem.getRoutes()) {
                System.out.println(route.getName());
            }

            System.out.println("Enter the route name:");
            String routeName = scanner.nextLine();

            System.out.println("Do you want to add stops to the train? (y/n)");
            String addStops = scanner.nextLine();

            ArrayList<String> stops = new ArrayList<>();
            if (addStops.equalsIgnoreCase("y")) {
                String addMoreStops;
                do {
                    // Print all available stations
                    System.out.println("Available stations:");
                    for (Station station : trainSystem.getStations()) {
                        System.out.println(station.getName());
                    }

                    System.out.println("Enter the station name:");
                    String stationName = scanner.nextLine();
                    stops.add(stationName);

                    System.out.println("Do you want to add more stops? (y/n)");
                    addMoreStops = scanner.nextLine();
                } while (addMoreStops.equalsIgnoreCase("y"));
            }

            // Convert ArrayList<String> to String[]
            String[] stopsArray = new String[stops.size()];
            stopsArray = stops.toArray(stopsArray);

            // Register the train
            trainSystem.registerTrain(trainName, routeName, stopsArray);

            System.out.println("Do you want to register another train? (y/n)");
            response = scanner.nextLine();
        }
    }
}
