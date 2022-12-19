package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * building class
 */
public class Building implements Serializable {

    private ArrayList<Room> allRoomsInABuilding;
    private String name;
    private String address;

    /**
     * constructor of the building class
     *
     * @param name    string of the name of the building
     * @param address string of the address of the building
     */
    public Building(String name, String address) {
        this.name = name;
        this.address = address;
        allRoomsInABuilding = new ArrayList<>();
    }

    /**
     * getter of the rooms in this building
     *
     * @return array list of the rooms
     */
    public ArrayList<Room> getAllRoomsInABuilding() {
        return allRoomsInABuilding;
    }

    /**
     * getter of the name of the building
     *
     * @return string value of the name of the building
     */
    public String getName() {
        return name;
    }

    /**
     * setter of the name of the bulding
     *
     * @param name string of the name of the building
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter of the address of the building
     *
     * @return string value of the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * setter if the address of the building
     *
     * @param address string of the address
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
