package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * person class which handles all the people in the system
 */
public class Person implements Serializable {
    private ArrayList<String> allEmailAddresses;
    private ArrayList<String> allNamesOfPeopleInTheUniversity;
    private ArrayList<String> roomsBookedByPerson;
    private LinkedHashMap<String, Room> roomsToBeCanceledAtPersonIsRemoved;
    private String personName;
    private Email emailAddress;

    /**
     * constructor of the class
     *
     * @param personName        string of the name of the person
     * @param emailAddressGiven string value of the email id of the person
     * @throws Exception throws invalid email and name has digit exceptions
     */
    public Person(String personName, String emailAddressGiven) throws Exception {
        this.emailAddress = new Email(emailAddressGiven);
        this.personName = personName;
        for (int i = 0; i < personName.length(); i++) {
            if (Character.isDigit(personName.charAt(i))) {
                throw new NameHasDigitException("Given name has a digit in it. Please try again without any digits.");
            }
        }
        if (this.emailAddress.validateEmail(emailAddressGiven).equals("1")) {
            throw new InvalidEmailException("Invalid email address! Please try again.");
        } else {
            allEmailAddresses = new ArrayList<>();
            roomsBookedByPerson = new ArrayList<>();
            allNamesOfPeopleInTheUniversity = new ArrayList<>();
            roomsToBeCanceledAtPersonIsRemoved = new LinkedHashMap<>();
            allEmailAddresses.add(this.emailAddress.getEmailAddress());
            allNamesOfPeopleInTheUniversity.add(this.getPersonName());
        }
    }

    /**
     * getter method for names of all the people
     *
     * @return array list of all the names of the people
     */
    public ArrayList<String> getAllNamesOfPeopleInTheUniversity() {
        return allNamesOfPeopleInTheUniversity;
    }

    /**
     * getter method for the emails of all the people in the system
     *
     * @return array list of string type
     */
    public ArrayList<String> getAllEmailAddresses() {
        return allEmailAddresses;
    }

    /**
     * getter of the rooms booked by a person
     *
     * @return array list of string type
     */
    public ArrayList<String> getRoomsBookedByPerson() {
        return roomsBookedByPerson;
    }

    /**
     * books the room for a person
     *
     * @param roomToBeBooked room type of the room to be booked
     * @param date           string of date
     * @param startTime      string of starting time
     * @param endTime        string of ending time
     * @return result of the response as a string
     */
    public String booking(Room roomToBeBooked, String date, String startTime, String endTime) {
        String result = roomToBeBooked.bookTheRoom(this.getEmailAddress(), date, startTime, endTime);
        if (result.equals("Booking done.")) {
            roomsBookedByPerson.add("Booked " + roomToBeBooked.getRoomName() + " for " + date + " from " + startTime + " to " + endTime);
            roomsToBeCanceledAtPersonIsRemoved.put(date + " " + startTime + " " + endTime, roomToBeBooked);
            result = "Aha! Booked the room for " + date + " from " + startTime + " to " + endTime + ".";
        }
        return result;
    }

    /**
     * cancels the booking
     *
     * @param roomTobeCancelled room type of the room to be cancelled
     * @param date              string of date
     * @param startTime         string of starting time
     * @param endTime           string value of ending time
     * @return result of the request as a string value
     */
    public String cancelBooking(Room roomTobeCancelled, String date, String startTime, String endTime) {
        String result = roomTobeCancelled.cancelRoomBooking(this.getEmailAddress(), date, startTime, endTime);
        if (result.equals("Cancelled the booking.")) {
            roomsBookedByPerson.remove("Booked " + roomTobeCancelled.getRoomName() + " from " + startTime + " to " + endTime);
            roomsBookedByPerson.add("Cancelled the booking made for the room " + roomTobeCancelled.getRoomName() + " in the time slot " + startTime + " to " + endTime);
            roomsToBeCanceledAtPersonIsRemoved.remove(startTime + " " + endTime);
            result = "Okay! Cancelled the booked the room from " + startTime + " to " + endTime + ".";
        }
        return result;
    }

    /**
     * called when a person is removed and all that person's booking should be cancelled
     *
     * @return string value of the response for the request
     */
    public String cancelAllBookingsAsPersonIsDeleted() {
        String result = "";
        ArrayList<String> s = new ArrayList<>(roomsToBeCanceledAtPersonIsRemoved.keySet());
        for (int i = 0; i < roomsToBeCanceledAtPersonIsRemoved.size(); i++) {
            Room bookingToBeCancelled = roomsToBeCanceledAtPersonIsRemoved.get(s.get(i));
            String[] startAndEndTime = s.get(i).split(" ");
            if (bookingToBeCancelled.cancelRoomBooking(this.getEmailAddress(), startAndEndTime[0], startAndEndTime[1], startAndEndTime[2]).equals("Cancelled the booking.")) {
                result = " All the bookings made by this person are also cancelled.";
            }
        }
        allEmailAddresses.remove(this.getEmailAddress());
        allNamesOfPeopleInTheUniversity.remove(this.getPersonName());
        roomsBookedByPerson.clear();
        roomsToBeCanceledAtPersonIsRemoved.clear();
        return result;
    }

    /**
     * getter method for the name of the person
     *
     * @return string of the name of the person
     */
    public String getPersonName() {
        return personName;
    }

    /**
     * getter of the email address
     *
     * @return string of the email id
     */
    public String getEmailAddress() {
        return emailAddress.getEmailAddress();
    }
}
