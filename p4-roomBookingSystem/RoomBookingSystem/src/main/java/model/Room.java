package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class responsible for room related things in the system
 */
public class Room implements Serializable {
    private ArrayList<String> slotsForBookingChart;
    private String roomName;
    private String buildingName;
    private LinkedHashMap<String, String> listOfBookings;
    private LinkedHashMap<String, LinkedHashMap<String, Boolean>> allBookings;

    /**
     * constructor method of the class
     *
     * @param roomName     string value of room name
     * @param buildingName string value of name of the building
     */
    public Room(String roomName, String buildingName) {
        this.roomName = roomName;
        this.buildingName = buildingName;
        slotsForBookingChart = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 60; j += 5) {
                slotsForBookingChart.add(i + ":" + j);
            }
        }
        listOfBookings = new LinkedHashMap<>();
        allBookings = new LinkedHashMap<>();
    }

    /**
     * checks and validates the time
     *
     * @param timeFormat string value of the given time format
     * @return boolean value if the time is as per required format or not
     */
    public static boolean checkGivenTimeFormat(String timeFormat) {
        Pattern requiredPattern = Pattern.compile("[^0-9:]");
        Matcher anyMatch = requiredPattern.matcher(timeFormat);
        if (anyMatch.find()) {
            return false;
        }
        String[] givenTimeFormat = timeFormat.split(":");
        if (givenTimeFormat.length == 2 && Integer.parseInt(givenTimeFormat[0]) < 24 && Integer.parseInt(givenTimeFormat[1]) < 60) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * checks and validates the date and also if it is greater than current date
     *
     * @param date string of the given date
     * @return boolean value which says the validity of the given date
     */
    public static boolean checkDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        simpleDateFormat.setLenient(false);
        try {
            Date currentDate = new Date();
            Date givenDate = simpleDateFormat.parse(date.trim());
            ;
            if (givenDate.before(currentDate)) {
                return false;
            }
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * getter of the all bookings which is the chart of all the booking made
     *
     * @return map of the bookings
     */
    public LinkedHashMap<String, LinkedHashMap<String, Boolean>> getAllBookings() {
        return allBookings;
    }

    /**
     * getter method for the slots of the booking
     *
     * @return array list of string type
     */
    public ArrayList<String> getSlotsForBookingChart() {
        return slotsForBookingChart;
    }

    /**
     * getter of the list of bookings made for this room
     *
     * @return map of the bookings made
     */
    public LinkedHashMap<String, String> getListOfBookings() {
        return listOfBookings;
    }

    /**
     * getter method of the name of the building this room is in
     *
     * @return string value
     */
    public String getBuildingName() {
        return buildingName;
    }

    /**
     * method which changes the name of the building
     *
     * @param buildingName string of the name of the building
     */
    public void changeBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    /**
     * getter of the room name
     *
     * @return string of the room name
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * method that changes the name of the room
     *
     * @param roomName string of room name
     */
    public void changeRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * removes the room
     */
    public void removeRoom() {
        this.roomName = null;
        this.allBookings = null;
    }

    /**
     * books the room which is called by the person booking it
     *
     * @param personEmail string of the name of the person
     * @param date        string of date of the booking
     * @param startTime   string of starting time
     * @param endTime     string of the ending time
     * @return response of the request in string form
     */
    public String bookTheRoom(String personEmail, String date, String startTime, String endTime) {
        String statusOfBooking = checkGivenTimeInterval(1, date, startTime, endTime);
        if (statusOfBooking.equals("All unavailable slots are: ")) {
            markTheSlotsBookedForTheRoomOrToCancel(1, date, startTime, endTime);
            String oldBooking = listOfBookings.get(personEmail);
            listOfBookings.put(personEmail, (oldBooking == null ? "" : oldBooking) + "\nBooked for " + date + " from " + startTime + " to " + endTime);
            return "Booking done.";
        }
        return "Booking un-successful, try again. " + statusOfBooking;
    }

    /**
     * marks the slots in passed-in time slot as booked or cancelled
     *
     * @param bookOrCancel int 1 to book and 2 to cancel
     * @param date         string of the date
     * @param startTime    string of the start time of the slot
     * @param endTime      string of the ending time of the slot
     */
    public void markTheSlotsBookedForTheRoomOrToCancel(int bookOrCancel, String date, String startTime, String endTime) {
        String[] splittingGivenStartTime = startTime.split(":");
        String[] splittingGivenEndTime = endTime.split(":");
        int startHour = Integer.parseInt(splittingGivenStartTime[0]);
        int startMinute = Integer.parseInt(splittingGivenStartTime[1]);
        int endHour = Integer.parseInt(splittingGivenEndTime[0]);
        int endMinute = Integer.parseInt(splittingGivenEndTime[1]);
        int startingIndex = slotsForBookingChart.indexOf(startHour + ":" + startMinute);
        int endingIndex = slotsForBookingChart.indexOf(endHour + ":" + endMinute);
        startingIndex++;
        boolean bookOrCancelBoolean = bookOrCancel == 1 ? true : false;
        if (bookOrCancel == 1) {
            while (startingIndex <= endingIndex) {
                if (!allBookings.get(date).get(slotsForBookingChart.get(startingIndex))) {
                    allBookings.get(date).put(slotsForBookingChart.get(startingIndex), bookOrCancelBoolean);
                    startingIndex++;
                }
            }
        } else {
            while (startingIndex <= endingIndex) {
                if (allBookings.get(date).get(slotsForBookingChart.get(startingIndex))) {
                    allBookings.get(date).put(slotsForBookingChart.get(startingIndex), bookOrCancelBoolean);
                    startingIndex++;
                }
            }
        }
    }

    /**
     * checks the given time interval for booking or cancelling accordingly
     *
     * @param forBookingOrCancelling int 1 for booking and 2 for cancelling
     * @param date                   string of the date
     * @param startTime              string of starting time
     * @param endTime                string of ending time
     * @return string of the response accordingly
     */
    public String checkGivenTimeInterval(int forBookingOrCancelling, String date, String startTime, String endTime) {
        if (checkDate(date)) {
            if (checkGivenTimeFormat(startTime) && checkGivenTimeFormat(endTime)) {
                if (!endTime.equals("0:0") && !endTime.equals("00:0") && !endTime.equals("00:00") && !endTime.equals("0:00")) {
                    String[] splittingGivenStartTime = startTime.split(":");
                    String[] splittingGivenEndTime = endTime.split(":");
                    int startHour = Integer.parseInt(splittingGivenStartTime[0]);
                    int startMinute = Integer.parseInt(splittingGivenStartTime[1]);
                    int endHour = Integer.parseInt(splittingGivenEndTime[0]);
                    int endMinute = Integer.parseInt(splittingGivenEndTime[1]);
                    if ((endHour - startHour > 0) || ((endHour - startHour == 0) && endMinute > startMinute)) {
                        if ((startMinute % 10 == 5 || startMinute % 10 == 0) && (endMinute % 10 == 5 || endMinute % 10 == 0)) {
                            if (forBookingOrCancelling == 1) {
                                return checkTheAvailabilityInSchedule(date, startHour, startMinute, endHour, endMinute);
                            } else {
                                return checkIfAllSlotsAreBooked(date, startHour, startMinute, endHour, endMinute);
                            }
                        } else {
                            return "Please enter the minutes in multiples of 5.";
                        }
                    } else {
                        return "Invalid time slots, ending time cannot be before the starting time.";
                    }
                } else {
                    return "Cannot book or cancel from 23:55 to 0:0 slot as it is across multiple dates.";
                }
            } else {
                return "Oh no! You have not entered the time format as required. Try with the acceptable format as h:m.";
            }
        } else {
            return "Enter a valid date which should be in dd-mm-yyyy and greater than current date.";
        }
    }

    /**
     * checks for all free slots
     *
     * @param date        string of date
     * @param startHour   int of the starting hour of the time slot
     * @param startMinute int of starting minute
     * @param endHour     int of the ending hour of the slot
     * @param endMinute   int of the ending minute of the slot
     * @return string of the status of the slots
     */
    public String checkTheAvailabilityInSchedule(String date, int startHour, int startMinute, int endHour, int endMinute) {
        if (allBookings.containsKey(date)) {
            int startingIndex = slotsForBookingChart.indexOf(startHour + ":" + startMinute);
            int endingIndex = slotsForBookingChart.indexOf(endHour + ":" + endMinute);
            startingIndex++;
            String bookedSlots = "";
            while (startingIndex <= endingIndex) {
                if (!allBookings.get(date).get(slotsForBookingChart.get(startingIndex))) {
                    startingIndex++;
                } else if (allBookings.get(date).get(slotsForBookingChart.get(startingIndex))) {
                    bookedSlots += slotsForBookingChart.get(startingIndex) + "\n";
                    startingIndex++;
                }
            }
            return "All unavailable slots are: " + bookedSlots;
        } else {
            LinkedHashMap<String, Boolean> slotsForAGivenDate = new LinkedHashMap<>();
            for (int i = 0; i < slotsForBookingChart.size(); i++) {
                slotsForAGivenDate.put(slotsForBookingChart.get(i), false);
            }
            allBookings.put(date, slotsForAGivenDate);
            return "All unavailable slots are: ";
        }
    }

    /**
     * checks if all the slots are booked or not
     *
     * @param date        string of date
     * @param startHour   int of the starting hour of the time slot
     * @param startMinute int of starting minute
     * @param endHour     int of the ending hour of the slot
     * @param endMinute   int of the ending minute of the slot
     * @return string value of the status of the slots
     */
    public String checkIfAllSlotsAreBooked(String date, int startHour, int startMinute, int endHour, int endMinute) {
        if (allBookings.containsKey(date)) {
            int startingIndex = slotsForBookingChart.indexOf(startHour + ":" + startMinute);
            int endingIndex = slotsForBookingChart.indexOf(endHour + ":" + endMinute);
            startingIndex++;
            int count = startingIndex, noBookingCount = startingIndex;
            while (startingIndex <= endingIndex) {
                if (allBookings.get(date).get(slotsForBookingChart.get(startingIndex))) {
                    startingIndex++;
                    count++;
                } else {
                    noBookingCount++;
                    startingIndex++;
                }
            }
            if (noBookingCount == endingIndex + 1) {
                return "No booking in the given slot to cancel.";
            } else if (count == endingIndex + 1 && !allBookings.get(date).get(slotsForBookingChart.get(count))) {
                return "All slots are booked.";
            } else {
                return "This time slot is part another booking as a subset or superset so cannot process your request.";
            }
        } else {
            return "No booking on the given date to cancel.";
        }
    }

    /**
     * cancels the booking done by a person
     *
     * @param personEmail string of the email address
     * @param date        string value of the date
     * @param startTime   string value of the starting time of the slot
     * @param endTime     string of the ending time
     * @return string value if the booking is cancelled or not
     */
    public String cancelRoomBooking(String personEmail, String date, String startTime, String endTime) {
        String statusOfBooking = checkGivenTimeInterval(2, date, startTime, endTime);
        if (statusOfBooking.equals("All slots are booked.")) {
            markTheSlotsBookedForTheRoomOrToCancel(2, date, startTime, endTime);
            String oldBooking = listOfBookings.get(personEmail);
            listOfBookings.put(personEmail, oldBooking + ". And then Cancelled the booking for " + date + " from " + startTime + " to " + endTime);
            return "Cancelled the booking.";
        }
        return statusOfBooking;
    }
}
