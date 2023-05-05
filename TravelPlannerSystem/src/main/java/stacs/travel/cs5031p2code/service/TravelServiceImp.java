package stacs.travel.cs5031p2code.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import stacs.travel.cs5031p2code.exception.InvalidStopException;
import stacs.travel.cs5031p2code.model.Route;
import stacs.travel.cs5031p2code.model.Stops;
import stacs.travel.cs5031p2code.model.Trip;
import stacs.travel.cs5031p2code.repository.CoreData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;

/**
 * The class for implementing all service method.
 *
 * @version 0.0.1
 */
@Service
public class TravelServiceImp extends TravelService {

    /**
     * The data storage.
     */
    private CoreData coreData;

    /**
     * New an object mapper
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    /**
     * This method for loading the data.
     */
    public synchronized void loadCoreData() {
        try {
            var json = FileUtils.readFileToString(new File("timeTableData.json"), "utf-8");
            // transform the json string to the normal data.
            this.coreData = StringUtils.isBlank(json) ? new CoreData() : mapper.readValue(json, CoreData.class);
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    /**
     * The method for saving the data.
     *
     * @param coreData core data.
     */
    public synchronized void saveCoreData(CoreData coreData) {
        try {
            // serialise the data to json string.
            var serialized = mapper.writeValueAsString(coreData);
            FileUtils.writeStringToFile(new File("timeTableData.json"), serialized, "utf-8", false);
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    /**
     * Returns all routes at a stop for a given time.
     *
     * @param stopName  name of stop.
     * @param timeOfDay time of day.
     * @return ArrayList of all applicable Routes.
     */
    public ArrayList<Route> allRoutesAtStopAndTime(String stopName, String timeOfDay) {
        ArrayList<Route> routes = new ArrayList<>();
        if (!"".equals(stopName) && !"".equals(timeOfDay)) {
            loadCoreData();
            for (int i = 0; i < getRouteList().size(); i++) {
                Route route = getRouteList().get(i);
                for (Trip trip : route.getTrips()) {
                    TreeMap<String, Stops> routeTimeTable = trip.getTimeTableOfTrip();
                    for (String time : routeTimeTable.keySet()) {
                        if (time.equals(timeOfDay) && routeTimeTable.get(time).getStopName().equals(stopName)) {
                            routes.add(route);
                        }
                    }
                }
            }
        }
        return routes;
    }

    /**
     * The method for listing all route.
     *
     * @return return the route list.
     */
    public ArrayList<Route> getRouteList() {
        loadCoreData();
        return coreData.getListOfAllRoutes();
    }

    /**
     * Show all available times when given the stop name.
     *
     * @param stopName The stop name.
     * @return return all available times.
     * @throws InvalidStopException the invalid stop exception.
     */
    public ArrayList<String> stopTimeTable(String stopName) throws InvalidStopException {
        if (!"".equals(stopName)) {
            loadCoreData();
            // for storing all available time.
            ArrayList<String> timeTable = new ArrayList<>();
            for (int i = 0; i < coreData.getListOfAllRoutes().size(); i++) {
                Route route = coreData.getListOfAllRoutes().get(i);
                for (Trip trip : route.getTrips()) {
                    TreeMap<String, Stops> routeTimeTable = trip.getTimeTableOfTrip();
                    for (String time : routeTimeTable.keySet()) {
                        if (routeTimeTable.get(time).getStopName().equals(stopName)) {
                            // The return message.
                            String timeAndRoute = route.getRouteName() + " at " + time;
                            timeTable.add(timeAndRoute);
                        }
                    }
                }
            }
            if (timeTable.size() == 0) {
                throw new InvalidStopException("Stop does not exist");
            }
            return timeTable;
        } else {
            throw new InvalidStopException("Stop name cannot be null. Try again");
        }
    }

    /**
     * Returns all routes that pass by stop.
     *
     * @param stopName name of stop.
     * @return ArrayList of Routes.
     */
    public ArrayList<Route> allRoutesAtStop(String stopName) {
        ArrayList<Route> routes = new ArrayList<>();
        if (!"".equals(stopName)) {
            loadCoreData();
            for (int i = 0; i < getRouteList().size(); i++) {
                Route route = getRouteList().get(i);
                for (Stops stop : route.getStopsInRoute()) {
                    if (stop.getStopName().equals(stopName)) {
                        routes.add(route);
                    }
                }
            }
        }
        return routes;
    }

    /**
     * The method for checking whether this stop is existed or not.
     *
     * @param routeName the route name.
     * @param stopName  the stop name.
     * @return return message.
     */
    private String stopExistsOrNot(String routeName, String stopName) {
        for (Route route : coreData.getListOfAllRoutes()) {
            if (route.getRouteName().equals(routeName) && route.stopExistsInRoute(stopName)) {
                return "Stop already exists in route " + route.getRouteName();
            }
        }
        return "Its a new stop";
    }

    /**
     * The method for adding a new stop into a route.
     *
     * @param stopName  the new stop name.
     * @param routeName the route name.
     * @param before    the before stop name.
     * @return return the message.
     * @throws InvalidStopException invalid stop exception.
     */
    public String addStopToRoute(String stopName, String routeName, String before) throws InvalidStopException {
        if (!"".equals(stopName) && !"".equals(routeName)) {
            loadCoreData();
            // check whether the new stop existed.
            String resultForNewStop = stopExistsOrNot(routeName, stopName);
            // check whether the before stop existed.
            String resultForBeforeStop = stopExistsOrNot(routeName, before);
            // the result
            String result = "";
            // if this new stop is not existed in the route.
            if (resultForNewStop.equals("Its a new stop")) {
                Stops newStop = new Stops(stopName);
                Route routeForNewStop = null;
                for (Route route : coreData.getListOfAllRoutes()) {
                    if (route.getRouteName().equals(routeName)) {
                        routeForNewStop = route;
                    }
                }
                if (routeForNewStop == null) {
                    throw new InvalidStopException("Route does not exists, try again");
                }
                // add the stop into route.
                routeForNewStop.addStopToRoute(newStop, before);
                // update the timetable.
                routeForNewStop.addStopToTimeTable(newStop, before);
                saveCoreData(coreData);
                String beforeOrAfter = "";
                if ("".equals(before)) {
                    // Don't have the before stop.
                    beforeOrAfter = " at the end and updated the route destination";
                } else if (resultForBeforeStop.equals("Its a new stop")) {
                    result = "The given before stop does not exist";
                    return result;
                } else {
                    beforeOrAfter = " before " + before;
                }
                return "Added " + stopName + " in " + routeForNewStop.getRouteName() + beforeOrAfter;
            } else {
                // if the new stop is existed in this route.
                return resultForNewStop;
            }
        } else {
            throw new InvalidStopException("Stop name or route name cannot be null. Try again");
        }
    }

    /**
     * The method for getting all stop.
     *
     * @return return all stop.
     */
    public List<Stops> getStopList() {
        loadCoreData();
        var routeList = this.coreData.getListOfAllRoutes();
        return routeList.stream()
                .map(Route::getStopsInRoute)
                .flatMap(Collection::stream)
                .distinct()
                .toList();
    }
}
