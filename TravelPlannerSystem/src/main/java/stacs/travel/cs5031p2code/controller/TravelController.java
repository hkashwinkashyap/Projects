package stacs.travel.cs5031p2code.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stacs.travel.cs5031p2code.exception.InvalidStopException;
import stacs.travel.cs5031p2code.model.Route;
import stacs.travel.cs5031p2code.model.Stops;
import stacs.travel.cs5031p2code.service.TravelService;
import stacs.travel.cs5031p2code.utils.ResponseResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Controller within the MVC model. Makes api calls to the model.
 *
 * @version 0.0.1
 */
@RestController
@CrossOrigin
public class TravelController {
    @Autowired
    private TravelService travelService;

    /**
     * The api for getting the route list.
     *
     * @return return the route list.
     */
    @GetMapping("/route")
    public ResponseResult<ArrayList<Route>> getRouteList() {
        return ResponseResult.<ArrayList<Route>>builder()
                .code(200)
                .message(null)
                .data(travelService.getRouteList()).build();
    }

    /**
     * The api for adding stop to certain route.
     *
     * @param requestTransfer including stopName, routeName, and beforeStopName.
     * @return return null data and some message.
     */
    @PostMapping("/route")
    public ResponseResult<Void> addStopToRoute(@RequestBody Map<String, String> requestTransfer) {
        try {
            return ResponseResult.<Void>builder()
                    .code(200)
                    .message(travelService.addStopToRoute(
                            requestTransfer.get("stopName"),
                            requestTransfer.get("routeName"),
                            requestTransfer.get("beforeStopName")))
                    .data(null)
                    .build();
        } catch (InvalidStopException e) {
            return ResponseResult.<Void>builder()
                    .code(400)
                    .message(e.getMessage())
                    .data(null)
                    .build();
        }
    }

    /**
     * The api for listing all route given stopName.
     *
     * @param stopName the name of stop.
     * @return return the routes.
     */
    @GetMapping("/route/{stop}")
    public ResponseResult<ArrayList<Route>> allRoutesAtStop(@PathVariable(name = "stop") String stopName) {
        return ResponseResult.<ArrayList<Route>>builder()
                .code(200)
                .message(null)
                .data(travelService.allRoutesAtStop(stopName))
                .build();
    }

    /**
     * The api for showing all routes given stop and time.
     *
     * @param stopName  the name of stop.
     * @param timeOfDay the time.
     * @return return a list of route.
     */
    @GetMapping("/route/{stop}/{time}")
    public ResponseResult<ArrayList<Route>> allRoutesAtStop(@PathVariable(name = "stop") String stopName,
                                                            @PathVariable(name = "time") String timeOfDay) {
        return ResponseResult.<ArrayList<Route>>builder()
                .code(200)
                .message(null)
                .data(travelService.allRoutesAtStopAndTime(stopName, timeOfDay))
                .build();
    }

    /**
     * List the all times when given a stop.
     *
     * @param stopName the name of stop.
     * @return return a list of time string.
     * Format: routeName "at" time
     */
    @GetMapping("/time/{stop}")
    public ResponseResult<ArrayList<String>> stopTimeTable(@PathVariable(name = "stop") String stopName) {
        try {
            return ResponseResult.<ArrayList<String>>builder()
                    .code(200)
                    .message(null)
                    .data(travelService.stopTimeTable(stopName))
                    .build();
        } catch (InvalidStopException e) {
            return ResponseResult.<ArrayList<String>>builder()
                    .code(400)
                    .message(String.valueOf(e))
                    .data(null)
                    .build();
        }
    }

    /**
     * The api for getting all stops.
     *
     * @return return the stoplist.
     */
    @GetMapping("/stop")
    public ResponseResult<List<Stops>> getStopList() {
        var stopList = travelService.getStopList();
        if (stopList == null || stopList.size() == 0) {
            return ResponseResult.<List<Stops>>builder()
                    .code(400)
                    .message("The stop list is null!")
                    .data(null)
                    .build();
        }
        return ResponseResult.<List<Stops>>builder()
                .code(200)
                .message(null)
                .data(stopList)
                .build();
    }
}
