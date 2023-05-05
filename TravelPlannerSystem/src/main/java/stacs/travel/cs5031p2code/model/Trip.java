package stacs.travel.cs5031p2code.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * The class for Trip.
 *
 * @version 0.0.1
 */
@JsonSerialize
public class Trip {
    /**
     * Hours in day.
     */
    private static final int HOURS_IN_DAY = 24;
    /**
     * Minutes in hour.
     */
    private static final int MINUTES_IN_HOUR = 60;
    /**
     * Timetable increment.
     */
    private static final int TEN_MIN_INCREMENT = 10;
    /**
     * For representing the timetable in a trip.
     * String means the time string.
     * Stops mean the stop.
     */
    private TreeMap<String, Stops> timeTableOfTrip;

    /**
     * For representing the time of whole day.
     */
    private final ArrayList<String> timeListForADay;

    /**
     * The getter of timeTableOfTrip.
     *
     * @return return the result.
     */
    public TreeMap<String, Stops> getTimeTableOfTrip() {
        return timeTableOfTrip;
    }

    /**
     * The setter of timeTableOfTrip.
     *
     * @param timeTableOfTrip the timeTableOfTrip.
     */
    public void setTimeTableOfTrip(TreeMap<String, Stops> timeTableOfTrip) {
        this.timeTableOfTrip = timeTableOfTrip;
    }

    /**
     * The constructor method of Trip.
     */
    public Trip() {
        this.timeTableOfTrip = new TreeMap<>();
        this.timeListForADay = new ArrayList<>();
        for (int i = 0; i < HOURS_IN_DAY; i++) {
            for (int j = 0; j < MINUTES_IN_HOUR; j += TEN_MIN_INCREMENT) {
                // format the hour and minute, then add into timeListForADay.
                timeListForADay.add(String.format("%02d", i) + ":" + String.format("%02d", j));
            }
        }
    }

    /**
     * The method for updating timetable when adding a new stop into a route.
     *
     * @param stops          the new stop.
     * @param beforeStopName the before stop name.
     */
    public void addStopToTimeTable(Stops stops, String beforeStopName) {
        String newStopTime = "";
        // new timetable.
        TreeMap<String, Stops> newTimeTable = new TreeMap<>();
        // traversal the time ---- the key of one trip.
        for (String time : timeTableOfTrip.keySet()) {
            // find the beforeStopName.
            if (timeTableOfTrip.get(time).getStopName().equals(beforeStopName)) {
                newStopTime = time;
                // put this time into the new stop time.
                newTimeTable.put(time, stops);
                break;
            } else {
                // not the before stop, then put the same time into timetable.
                newStopTime = time;
                newTimeTable.put(time, timeTableOfTrip.get(time));
            }
        }
        // the rest of stops.
        int untilTheEndOfTimeTable = timeTableOfTrip.size() - newTimeTable.size() + 1;
        // the new stop is destination.
        if (untilTheEndOfTimeTable == 1) {
            if (newTimeTable.containsValue(stops)) {
                newTimeTable.put(timeListForADay.get(timeListForADay.indexOf(newStopTime) + 1), timeTableOfTrip.get(newStopTime));
            } else {
                newTimeTable.put(timeListForADay.get(timeListForADay.indexOf(newStopTime) + 1), stops);
            }
        } else {
            // update the rest timetable.
            int indexOfNewStopTime = timeListForADay.indexOf(newStopTime);
            newTimeTable.put(timeListForADay.get(indexOfNewStopTime + 1), timeTableOfTrip.get(newStopTime));
            indexOfNewStopTime += 2;
            for (int i = 0; i < untilTheEndOfTimeTable - 1; i++) {
                newTimeTable.put(timeListForADay.get(indexOfNewStopTime), timeTableOfTrip.get(timeListForADay.get(indexOfNewStopTime - 1)));
                indexOfNewStopTime += 1;
            }
        }
        this.setTimeTableOfTrip(newTimeTable);
    }
}
