package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * unified model of the room booking system
 */
public class UniversityRoomBookingModel implements Serializable {
    private PropertyChangeSupport notifier;
    private ArrayList<Building> allBuildingsInUniversity;
    private ArrayList<String> allBuildingNames;
    private ArrayList<Person> peopleInUniversity;
    private ArrayList<String> slotsForBookingChart;
    private HashMap<String, Room> roomNames;
    private ArrayList<String> allEmailAddresses;
    private ArrayList<String> allNamesOfPeopleInTheUniversity;

    /**
     * constructor of the unified model of the room booking system
     */
    public UniversityRoomBookingModel() {
        slotsForBookingChart = new ArrayList<>();
        allBuildingNames = new ArrayList<>();
        allBuildingsInUniversity = new ArrayList<>();
        peopleInUniversity = new ArrayList<>();
        roomNames = new HashMap<>();
        allEmailAddresses = new ArrayList<>();
        allNamesOfPeopleInTheUniversity = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 60; j += 5) {
                slotsForBookingChart.add(i + ":" + j);
            }
        }
        this.notifier = new PropertyChangeSupport(this);
    }

    /**
     * listeners add themselves by calling this
     *
     * @param listener observer's instance
     */
    public void addListener(PropertyChangeListener listener) {
        notifier.addPropertyChangeListener(listener);
    }

    /**
     * updates the listeners
     */
    private void update() {
        notifier.firePropertyChange(null);
    }

    /**
     * getter of people in university
     *
     * @return array list of people type
     */
    public ArrayList<Person> getPeopleInUniversity() {
        return peopleInUniversity;
    }

    /**
     * setter of the people in the university
     *
     * @param person instance of Person is passed
     */
    public void setPeopleInUniversity(Person person) {
        peopleInUniversity.add(person);
    }

    /**
     * getter method of the all buildings in the university
     *
     * @return array list of the buildings
     */
    public ArrayList<Building> getAllBuildingsInUniversity() {
        return allBuildingsInUniversity;
    }

    /**
     * setter of all buildings in the university
     *
     * @param building instance of the building
     */
    public void setAllBuildingsInUniversity(Building building) {
        allBuildingsInUniversity.add(building);
    }

    /**
     * getter of the all building names
     *
     * @return array list of the building names
     */
    public ArrayList<String> getAllBuildingNames() {
        return allBuildingNames;
    }

    /**
     * setter method of all building names in the university
     *
     * @param buildingName string valid of the name of the building
     */
    public void setAllBuildingNames(String buildingName) {
        allBuildingNames.add(buildingName);
    }

    /**
     * adds the person in the system
     *
     * @param personNameToBeAdded string of the person's name
     * @param emailAddress        string of the email address
     * @return string which says the result of the request
     * @throws Exception might return invalid email exception
     */
    public String addPerson(String personNameToBeAdded, String emailAddress) throws Exception {
        if (peopleInUniversity != null) {
            for (Person person : peopleInUniversity) {
                if (person.getEmailAddress().equals(emailAddress)) {
                    return "This Email id already exists in the university. Try with a new one.";
                }
            }
        }
        Person person = new Person(personNameToBeAdded, emailAddress);
        peopleInUniversity.add(person);
        allEmailAddresses.add(person.getEmailAddress());
        allNamesOfPeopleInTheUniversity.add(person.getPersonName());
        if (peopleInUniversity != null) {
            for (int i = 0; i < peopleInUniversity.size(); i++) {
                if (peopleInUniversity.get(i).getPersonName().equals(personNameToBeAdded)) {
                    return "Nice! You added this Person to the University successfully!";
                }
            }
        }
        return "Oh no! There was some issue, please try again.";
    }

    /**
     * removes the person from the university
     *
     * @param personEmailToBeRemoved string of the email address of the person
     * @return string which says the result of the request
     */
    public String removePerson(String personEmailToBeRemoved) {
        if (peopleInUniversity != null) {
            Person personToBeRemoved;
            for (int i = 0; i < peopleInUniversity.size(); i++) {
                if (peopleInUniversity.get(i).getEmailAddress().equals(personEmailToBeRemoved)) {
                    personToBeRemoved = peopleInUniversity.get(i);
                    String result = personToBeRemoved.cancelAllBookingsAsPersonIsDeleted();
                    peopleInUniversity.remove(i);
                    personToBeRemoved = null;
                    result = "Haha! This Person has been removed from the University." + result;
                    return result;
                }
            }
            return "No person with that email and name exists to remove.";
        }
        return "No people in university to remove.";
    }

    /**
     * adds the building into the system
     *
     * @param buildingNameToBeAdded string of the name of the building
     * @param addressOfThatBuilding string of the address of the building
     * @return string which says the result of the request
     * @throws Exception throws name already exists exception
     */
    public String addBuilding(String buildingNameToBeAdded, String addressOfThatBuilding) throws Exception {
        if (getAllBuildingNames() != null && getAllBuildingNames().contains(buildingNameToBeAdded)) {
            throw new NameAlreadyExistsException("Building name already exists. Try with a new name.");
        }
        if (allBuildingsInUniversity != null) {
            for (Building building : allBuildingsInUniversity) {
                if (building.getName().equals(buildingNameToBeAdded)) {
                    return "This building already exists in the university.";
                }
            }
        }
        allBuildingsInUniversity.add(new Building(buildingNameToBeAdded, addressOfThatBuilding));
        allBuildingNames.add(buildingNameToBeAdded);
        if (allBuildingsInUniversity != null) {
            for (int i = 0; i < allBuildingsInUniversity.size(); i++) {
                if (allBuildingsInUniversity.get(i).getName().equals(buildingNameToBeAdded)) {
                    return "Great! Some of the University's real estate has been utilised to accommodate this new building. Thanks for the pollution you've created for that.";
                }
            }
        }
        return "Damn! This is embarrassing. Try again buddy!";
    }

    /**
     * removes the building from the model
     *
     * @param buildingNameTobeBeRemoved string value of the name of the building
     * @return string which says the result of the request
     */
    public String removeBuilding(String buildingNameTobeBeRemoved) {
        if (allBuildingsInUniversity != null) {
            Building buildingToBeRemoved;
            for (int i = 0; i < allBuildingsInUniversity.size(); i++) {
                if (allBuildingsInUniversity.get(i).getName().equals(buildingNameTobeBeRemoved)) {
                    buildingToBeRemoved = allBuildingsInUniversity.get(i);
                    allBuildingsInUniversity.remove(buildingToBeRemoved);
                    allBuildingNames.remove(buildingNameTobeBeRemoved);
                    buildingToBeRemoved = null;
                    return "Haha! This Building has been removed from the University. More pollution in destructing a building. Plant some trees in that place at-least!";
                }
            }
        }
        return "No buildings are there to remove.";
    }

    /**
     * adds or removes the room based on the int argument
     *
     * @param addOrRemove  adds if this is 1 and removes if it's 2
     * @param roomName     string value of the room name
     * @param buildingName string of the name of the building
     * @return string which says the result of the request
     * @throws Exception throws name already exits exception
     */
    public String addOrRemoveARoom(int addOrRemove, String roomName, String buildingName) throws Exception {
        if (getAllBuildingNames() != null && getAllBuildingNames().contains(buildingName)) {
            if (allBuildingsInUniversity != null) {
                if (allBuildingNames.contains(buildingName)) {
                    for (Building building : allBuildingsInUniversity) {
                        for (Room room : building.getAllRoomsInABuilding()) {
                            if (room.getRoomName().equals(roomName) && room.getBuildingName().equals(buildingName)) {
                                if (addOrRemove == 1) {
                                    if (roomNames != null && roomNames.keySet().contains(roomName)) {
                                        throw new NameAlreadyExistsException("Room name already exists. Try with a new name.");
                                    }
                                } else {
                                    building.getAllRoomsInABuilding().remove(room);
                                    room.removeRoom();
                                    roomNames.remove(roomName);
                                    return "Removed the room. Cheers!";
                                }
                            }
                        }
                        if (building.getName().equals(buildingName)) {
                            if (addOrRemove == 1) {
                                Room room = new Room(roomName, buildingName);
                                building.getAllRoomsInABuilding().add(room);
                                roomNames.put(roomName, room);
                                return "Room with name " + roomName + " created and added to the building with name " + buildingName + ".";
                            }
                        }
                    }
                } else {
                    return "Add a building with that name first.";
                }
            } else {
                return "Add a building first.";
            }
            return "No room exists with that name to remove.";
        } else {
            throw new RoomWithoutBuildingException("Building with that name doesn't exist. Add a building first and then try.");
        }
    }

    /**
     * books or cancels the room based on the int argument passed
     *
     * @param bookOrCancel                           books if it's 1 and cancels if the value is 2
     * @param stringValueOfPersonEmailBookingTheRoom string of the email id of the person
     * @param date                                   date's string value
     * @param roomTobeBooked                         string form of the room name
     * @param startTime                              string of the starting time
     * @param endTime                                string of ending time
     * @return string which says the result of the request
     */
    public String bookOrCancelARoomByAPerson(int bookOrCancel, String
            stringValueOfPersonEmailBookingTheRoom, String date, String roomTobeBooked, String startTime, String endTime) {
        if (peopleInUniversity.size() != 0) {
            String email = "";
            for (Person person : peopleInUniversity) {
                if (person.getEmailAddress().equals(stringValueOfPersonEmailBookingTheRoom)) {
                    email = person.getEmailAddress();
                }
            }
            if (email.length() > 1) {
                Person personBookingTheRoom;
                for (int i = 0; i < peopleInUniversity.size(); i++) {
                    if (peopleInUniversity.get(i).getEmailAddress().equals(stringValueOfPersonEmailBookingTheRoom)) {
                        personBookingTheRoom = peopleInUniversity.get(i);
                        if (allBuildingsInUniversity.size() != 0) {
                            Room roomToBeBookedByThePerson;
                            for (Building building : allBuildingsInUniversity) {
                                for (Room room : building.getAllRoomsInABuilding()) {
                                    if (room.getRoomName().equals(roomTobeBooked)) {
                                        roomToBeBookedByThePerson = room;
                                        if (bookOrCancel == 1) {
                                            return personBookingTheRoom.booking(roomToBeBookedByThePerson, date, startTime, endTime);
                                        } else if (bookOrCancel == 2) {
                                            return personBookingTheRoom.cancelBooking(roomToBeBookedByThePerson, date, startTime, endTime);
                                        }
                                    }
                                }
                            }
                        } else {
                            return "Well! You gotta have a building, and it must have this room first to book it.";
                        }
                    }
                }
            } else {
                return "Oh no! No person with that name and email exists in the University. Try once you've added this person to book the room.";
            }
        } else {
            return "Haha! Add some people in the University and then try.";
        }
        return "The room doesn't exist so try adding a room first and then try.";
    }

    /**
     * gives the rooms available at the given time
     *
     * @param timeStart string of the starting time
     * @param date      date's string value
     * @return string which says the result of the request
     */
    public String roomsAvailableAtGivenTime(String timeStart, String date) {
        if (Room.checkDate(date)) {
            if (Room.checkGivenTimeFormat(timeStart)) {
                if (!timeStart.equals("23:55")) {
                    String result = "The following rooms are available for given time to 5 minutes from that:\n";
                    String[] startingTime = timeStart.split(":");
                    int startingHour = Integer.parseInt(startingTime[0]);
                    int startingMinute = Integer.parseInt(startingTime[1]);
                    if (startingMinute % 10 == 5 || startingMinute % 10 == 0) {
                        if (startingMinute == 55) {
                            startingHour++;
                            startingMinute = 0;
                        } else {
                            startingMinute += 5;
                        }
                        timeStart = startingHour + ":" + startingMinute;
                        if (allBuildingsInUniversity.size() != 0) {
                            int startingIndex = slotsForBookingChart.indexOf(timeStart);
                            for (int i = 0; i < allBuildingsInUniversity.size(); i++) {
                                for (Room room : allBuildingsInUniversity.get(i).getAllRoomsInABuilding()) {
                                    int index = room.getSlotsForBookingChart().indexOf(timeStart);
                                    if (!room.getAllBookings().containsKey(date)) {
                                        result += room.getRoomName() + "\n";
                                    } else if (room.getAllBookings().containsKey(date) && !room.getAllBookings().get(date).get(slotsForBookingChart.get(startingIndex))) {
                                        result += room.getRoomName() + "\n";
                                    }
                                }
                            }
                            return result;
                        } else {
                            return "No Buildings are there which means no rooms also, so nothing to show. Add them first.";
                        }
                    } else {
                        return "Please give the minutes in units of 5.";
                    }
                } else {
                    return "Can't make booking at that time as it spreads across multiple dates.";
                }
            } else {
                return "Oh no! You have not entered the time format as required. Try with the acceptable format.";
            }
        } else {
            return "Invalid date, try again.";
        }
    }

    /**
     * gives all rooms available in the time slot given
     *
     * @param startTime string of starting time
     * @param endTime   string of ending time
     * @param date      string of date
     * @return string which says the result of the request
     */
    public String roomsAvailableInTheGivenTimeSlot(String startTime, String endTime, String date) {
        if (Room.checkDate(date)) {
            if (Room.checkGivenTimeFormat(startTime) && Room.checkGivenTimeFormat(endTime)) {
                if (!startTime.equals("23:55") && !endTime.equals("0:0")) {
                    String result = "The following rooms are available for given time slot:\n";
                    String[] splittingGivenStartTime = startTime.split(":");
                    String[] splittingGivenEndTime = endTime.split(":");
                    int startHour = Integer.parseInt(splittingGivenStartTime[0]);
                    int endHour = Integer.parseInt(splittingGivenEndTime[0]);
                    int startMinute = Integer.parseInt(splittingGivenStartTime[1]);
                    int endMinute = Integer.parseInt(splittingGivenEndTime[1]);
                    if ((startMinute % 10 == 5 || startMinute % 10 == 0) && (endMinute % 10 == 5 || endMinute % 10 == 0)) {
                        if (allBuildingsInUniversity.size() != 0) {
                            for (int i = 0; i < allBuildingsInUniversity.size(); i++) {
                                String roomSlotsAvailable = "";
                                for (Room room : allBuildingsInUniversity.get(i).getAllRoomsInABuilding()) {
                                    String roomIsAvailable = room.checkIfAllSlotsAreBooked(date, startHour, startMinute, endHour, endMinute);
                                    if (roomIsAvailable.equals("No booking in the given slot to cancel.") || roomIsAvailable.equals("No booking on the given date to cancel.")) {
                                        roomSlotsAvailable = room.getRoomName() + "\n";
                                    }
                                    result += roomSlotsAvailable;
                                }
                            }
                            return result.equals("The following rooms are available for given time slot:\n") ? "No rooms are available for the entire time slot given." : result;
                        } else {
                            return "No Buildings are there which means no rooms also, so nothing to show. Add them first.";
                        }
                    } else {
                        return "Please give the minutes in units of 5.";
                    }
                } else {
                    return "Can't make booking at that time, as it spreads across multiple dates.";
                }
            } else {
                return "Oh no! You have not entered the time format as required. Try with the acceptable format.";
            }
        } else {
            return "Invalid date, try again.";
        }
    }

    /**
     * gives all the bookings made by a person
     *
     * @param personName  string value of the person's name
     * @param personEmail string of the email id of the person
     * @return string which says the result of the request
     */
    public String viewAllBookingsByAPerson(String personName, String personEmail) {
        if (peopleInUniversity.size() != 0) {
            String email = "";
            for (Person person : peopleInUniversity) {
                if (person.getEmailAddress().equals(personEmail)) {
                    email = person.getEmailAddress();
                }
            }
            if (email.length() > 1) {
                Person personForBookings = null;
                String result = "Bookings made by " + personName + ":";
                for (Person person : peopleInUniversity) {
                    if (person.getEmailAddress().equals(personEmail)) {
                        personForBookings = person;
                    }
                }
                for (String s : personForBookings.getRoomsBookedByPerson()) {
                    result += s + "\n";
                }
                return result;
            } else {
                return "This person in not part of the university. Add first and then try.";
            }
        } else {
            return "Add few people and then try.";
        }
    }

    /**
     * gives schedule of a room in a given date
     *
     * @param roomNameGiven string value of the room name
     * @param date          string value of the date
     * @return string which says the result of the request
     */
    public String viewScheduleOfARoom(String roomNameGiven, String date) {
        if (Room.checkDate(date)) {
            if (allBuildingsInUniversity.size() != 0) {
                for (Building building : allBuildingsInUniversity) {
                    for (Room room : building.getAllRoomsInABuilding()) {
                        if (room.getRoomName().equals(roomNameGiven)) {
                            if (room.getAllBookings().containsKey(date)) {
                                String result = "The availability of slots are indicated by its roof time.\nMeaning if the room is booked from 10:00 to 10:05, \nit shows that 10:05 is Booked but not 10:00\nindicating the room is booked from 10:00 to 10:05.\n";
                                for (int i = 0; i < slotsForBookingChart.size(); i++) {
                                    result += slotsForBookingChart.get(i) + " - " + (room.getAllBookings().get(date).get(slotsForBookingChart.get(i)) ? "Booked" : "Available") + "\n";
                                }
                                return result;
                            } else {
                                return "The room is available all day on " + date + ".";
                            }
                        }
                    }
                }
                return "No room exists with that name.";
            } else {
                return "No buildings are there. Add few.";
            }
        } else {
            return "Invalid date or format. Try 'dd-mm-yyyy'.";
        }
    }

    /**
     * returns all the transactions done against a room
     *
     * @param roomName string of the room name
     * @return string which says the result of the request
     */
    public String listAllBookings(String roomName) {
        if (allBuildingsInUniversity.size() != 0) {
            for (Building building : allBuildingsInUniversity) {
                for (Room room : building.getAllRoomsInABuilding()) {
                    if (room.getRoomName().equals(roomName)) {
                        String result = "List of all the bookings for " + roomName + ":\n";
                        result += room.getListOfBookings().toString();
                        return result;
                    }
                }
            }
            return "No room exits with that name. Add one and then try.";
        } else {
            return "No buildings are there. Add few.";
        }
    }
}
