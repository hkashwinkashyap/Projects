package stacs.travel.cs5031p2code.repository;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import stacs.travel.cs5031p2code.model.Route;

import java.util.ArrayList;

/**
 * A class for representing the data of this system.
 *
 * @version 0.0.1
 */
@Data
@JsonSerialize
public class CoreData {

    /**
     * All the routes.
     */
    private ArrayList<Route> listOfAllRoutes;

    /**
     * The constructor method.
     */
    public CoreData() {
        this.listOfAllRoutes = new ArrayList<>();
    }
}
