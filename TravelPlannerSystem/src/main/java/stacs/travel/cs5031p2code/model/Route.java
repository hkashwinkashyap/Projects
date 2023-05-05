package stacs.travel.cs5031p2code.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * The class for route.
 *
 * @version 0.0.1
 */
@Data
@JsonSerialize
@NoArgsConstructor
public class Route {

    /**
     * The route name.
     */
    private String routeName;

    /**
     * The list of stops in the route.
     */
    private ArrayList<Stops> stopsInRoute;

    /**
     * The list for trips.
     * Trip means each row (timetable) in a certain route.
     */
    private ArrayList<Trip> trips;

    /**
     * The constructor method.
     *
     * @param routeName    the name of route.
     * @param stopsInRoute stops in route.
     */
    public Route(String routeName, ArrayList<Stops> stopsInRoute) {
        this.routeName = routeName;
        this.stopsInRoute = stopsInRoute;
        this.trips = new ArrayList<>();
    }

    /**
     * The method for checking whether stop contained in this route.
     *
     * @param stopName the name of stop.
     * @return return the result.
     */
    public boolean stopExistsInRoute(String stopName) {
        ArrayList<String> stopNames = new ArrayList<>();
        for (Stops stops : stopsInRoute) {
            stopNames.add(stops.getStopName());
        }
        return stopNames.contains(stopName);
    }

    /**
     * The method for adding a stop into route.
     *
     * @param stops the stop
     */
    public void addStopToRoute(Stops stops, String beforeName) {
        // The new stop will be viewed as destination.
        if (!stopExistsInRoute(beforeName)) {
            stopsInRoute.add(stops);
        } else {
            int i;
            for (i = 0; i < stopsInRoute.size(); i++) {
                if (stopsInRoute.get(i).getStopName().equals(beforeName)) {
                    break;
                }
            }
            // add the new stop to correct position.
            stopsInRoute.add(i, stops);
        }
    }

    /**
     * The method for updating trips when we add a new stop into route.
     *
     * @param stops      the new stop.
     * @param beforeName the before stop.
     */
    public void updateAllTrips(Stops stops, String beforeName) {
        for (Trip trip : this.getTrips()) {
            trip.addStopToTimeTable(stops, beforeName);
        }
    }

    /**
     * Update the timetable when we add stop to a route.
     *
     * @param stops          the new stop.
     * @param beforeStopName the before stopName.
     */
    public void addStopToTimeTable(Stops stops, String beforeStopName) {
        // If no beforeStop, meaning the new stop is a destination stop.
        if ("".equals(beforeStopName)) {
            // the new stop name ----> destination name
            String destinationStopName = stops.getStopName();
            // substring 0 to 5 means the prefix of each route, for example, "99 to ".
            String updatedRouteName = this.getRouteName().substring(0, 6) + destinationStopName;
            this.setRouteName(updatedRouteName);
        }
        this.updateAllTrips(stops, beforeStopName);
    }
}
