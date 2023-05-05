package stacs.travel.cs5031p2code.service;

import org.springframework.stereotype.Service;
import stacs.travel.cs5031p2code.exception.InvalidStopException;
import stacs.travel.cs5031p2code.model.Route;
import stacs.travel.cs5031p2code.model.Stops;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface of service.
 *
 * @version 0.0.1
 */
@Service
public abstract class TravelService {

    /**
     * Show all routes given stop and time.
     *
     * @param stopName  The name of stop.
     * @param timeOfDay The time.
     * @return return route list which meets the requirement.
     */
    public abstract ArrayList<Route> allRoutesAtStopAndTime(String stopName, String timeOfDay);

    /**
     * List the all times when given a stop.
     *
     * @param stopName The stop name.
     * @return return all available time when you are in this stop.
     * @throws InvalidStopException the invalid stop exception.
     */
    public abstract ArrayList<String> stopTimeTable(String stopName) throws InvalidStopException;

    /**
     * List all route through this stop.
     *
     * @param stopName The stop name.
     * @return return meeting route list.
     */
    public abstract ArrayList<Route> allRoutesAtStop(String stopName);

    /**
     * Add new stop into a route.
     *
     * @param stopName  the new stop name.
     * @param routeName the route name.
     * @param before    the before stop name.
     * @return return the message.
     * @throws InvalidStopException invalid stop exception.
     */
    public abstract String addStopToRoute(String stopName, String routeName, String before) throws InvalidStopException;

    /**
     * Show all route.
     *
     * @return return the route list.
     */
    public abstract ArrayList<Route> getRouteList();

    /**
     * The method for showing all stops.
     *
     * @return return stop list.
     */
    public abstract List<Stops> getStopList();
}
