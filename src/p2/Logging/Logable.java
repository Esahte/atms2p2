package p2.Logging;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import p2.Events.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Logable {

    private final StringProperty log = new SimpleStringProperty(this, "log", "");
    /* once an event is added to the log, it cannot be removed or changed. */
    protected List<Event> events = new ArrayList<Event>();

    public void addToLog(Event event) {
        events.add(event);
    }

    public int logSize() {
        return events.size();
    }

    public boolean contains(ArrayList<String> events) {
        ArrayList<String> logEvents = getEvents();
        return logEvents.containsAll(events);
    }

    public boolean containsInSequence(ArrayList<String> events) {
        ArrayList<String> logEvents = getEvents();
        return Collections.indexOfSubList(logEvents, events) != -1;
    }

    public boolean validate() {
        /*
         * 1. for the UI/GUI that you use, print information to show what step in the
         * validation you are doing. You must print the result of that step. This must
         * also be done in overridden methods in the concrete subclasses.
         *
         * 2. No duplicate events in log, aliases or otherwise.
         *
         * 3. Ask yourself, how many events can each object have per time instant?
         * Ensure that you check for that here.
         *
         * 4. ensure that you return the correct result
         */

        return true;
    }

    public ArrayList<String> getEvents() {
        ArrayList<String> events = new ArrayList<String>();
        if (this.events == null || this.events.isEmpty())
            return events;
        else {
            for (Event e : this.events)
                events.add(e.toString());
        }
        return events;
    }

    public ArrayList<String> getEvents(int time) {
        ArrayList<String> events = new ArrayList<String>();

        if (this.events != null && !this.events.isEmpty()) {
            ArrayList<Event> ets = new ArrayList<Event>();
            for (Event e : this.events)
                if (e.getTime() == time)
                    ets.add(e);

            for (Event e : ets)
                events.add(e.toString());

        }
        return events;
    }

    public ArrayList<String> getEvents(String object) {
        ArrayList<String> events = new ArrayList<String>();

        if (this.events != null && !this.events.isEmpty()) {
            ArrayList<Event> ets = new ArrayList<Event>();
            for (Event e : this.events)
                if (e.getObjectName().equals(object))
                    ets.add(e);

            for (Event e : ets)
                events.add(e.toString());
        }
        return events;
    }

    public ArrayList<String> getObjects() {
        ArrayList<String> objects = new ArrayList<String>();
        for (Event e : events)
            if (!objects.contains(e.getObjectName()))
                objects.add(e.getObjectName());
        return objects;
    }

    public int distinctObjects() {
        return getObjects().size();
    }

    @Override
    public String toString() {

        StringBuilder events = new StringBuilder("Events Log[");
        if (this.events == null || this.events.isEmpty())
            events.append("no events]");
        else {
            for (Event e : this.events)
                events.append(this.events.indexOf(e) == 0 ? "\n\t" : "\t").append(e).append(this.events.indexOf(e) != this.events.size() - 1 ? "\n" : "\n\t]");
        }
        return events.toString();
    }

    // Method to append text to the log
    public void addToLog(String message) {
        javafx.application.Platform.runLater(() -> {
            log.set(log.get() + message + "\n");
        });
    }

    // Property getter
    public StringProperty logProperty() {
        return log;
    }
}
