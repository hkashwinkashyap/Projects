package view.clui;

import controller.RoomBookingController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * class of the command line user interface of the system
 */
public class RoomBookingClUi implements Runnable {
    String[] addPersonArray, removePersonArray, addBuildingArray, removeBuildingArray, addRoomArray, removeRoomArray, bookRoomArray, cancelRoomArray, roomsGivenTimeArray, roomsGivenSlotArray, scheduleOfARoomArray, bookingByAPersonArray, saveArray, loadArray, quitArray, listAllBookingArray, helpArray;
    private RoomBookingController roomBookingController;
    private volatile boolean quit = false;

    /**
     * constructor of the class
     *
     * @param roomBookingController instance of the unified model of the system
     */
    public RoomBookingClUi(RoomBookingController roomBookingController) {
        this.roomBookingController = roomBookingController;
        addPersonArray = new String[]{"Add-person", "<personName>", "<emailAddress>"};
        removePersonArray = new String[]{"Remove-person", "<emailAddressOfThePerson>"};
        addBuildingArray = new String[]{"Add-building", "<buildingName>", "<buildingAddress>"};
        removeBuildingArray = new String[]{"Remove-building", "<buildingName>"};
        addRoomArray = new String[]{"Add-room", "<roomName>", "<buildingName>"};
        removeRoomArray = new String[]{"Remove-room", "<roomName>", "<buildingName>"};
        bookRoomArray = new String[]{"Book-room", "<personEmailAddress>", "<date in dd—mm—yyyy>", "<roomName>", "<startTimeOfTheBooking>", "<endTimeOfTheBooking>"};
        cancelRoomArray = new String[]{"Cancel-booking", "<personEmailAddressWhoMadeTheBooking>", "<date in dd—mm—yyyy>", "<roomName>", "<startTimeOfTheBooking>", "<endTimeOfTheBooking>"};
        roomsGivenTimeArray = new String[]{"Rooms-given-time", "<timeRequired>", "<date in dd—mm—yyyy>"};
        roomsGivenSlotArray = new String[]{"Rooms-slot", "<startTime>", "<endTime>", "<date in dd—mm—yyyy>"};
        scheduleOfARoomArray = new String[]{"Schedule-of-a-room", "<roomName>", "<date in dd—mm—yyyy>"};
        bookingByAPersonArray = new String[]{"Booking-by-a-person", "<personName>", "<personEmail>"};
        saveArray = new String[]{"Save"};
        loadArray = new String[]{"Load"};
        quitArray = new String[]{"Quit-cl-ui"};
        listAllBookingArray = new String[]{"List-of-all-bookings-for-a-room", "<roomName>"};
        helpArray = new String[]{"Help"};
    }

    /**
     * run method which is started when a thread is started which handles all the user's requests
     */
    public void run() {
        while (!quit) {
            System.out.println("CL UI Started");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            boolean closeClUi = false;
            System.out.println("Welcome to the University's room booking system. Type 'Help' to get the list of commands.");
            while (!closeClUi) {
                System.out.println("Please enter the command: ");
                String input = null;
                try {
                    input = bufferedReader.readLine();
                } catch (IOException e) {
                    System.out.println(e);
                    continue;
                }
                String[] inputWithCommands = input.split(" ");
                if (inputWithCommands[0].equals("Help")) {
                    if (inputWithCommands.length != helpArray.length) {
                        invalidInput();
                        continue;
                    }
                    System.out.println(roomBookingController.help());
                } else if (inputWithCommands[0].equals("List-of-all-bookings-for-a-room")) {
                    if (inputWithCommands.length != listAllBookingArray.length) {
                        invalidInput();
                        continue;
                    }
                    System.out.println(roomBookingController.listOfAllBookingForARoom(inputWithCommands[1]));
                } else if (inputWithCommands[0].equals("Add-person")) {
                    try {
                        if (inputWithCommands.length != addPersonArray.length) {
                            invalidInput();
                            continue;
                        }
                        System.out.println(roomBookingController.addPerson(inputWithCommands[1], inputWithCommands[2]));
                    } catch (Exception e) {
                        System.out.println(e);
                        continue;
                    }
                } else if (inputWithCommands[0].equals("Remove-person")) {
                    if (inputWithCommands.length != removePersonArray.length) {
                        invalidInput();
                        continue;
                    }
                    System.out.println(roomBookingController.removePerson(inputWithCommands[1]));
                } else if (inputWithCommands[0].equals("Add-building")) {
                    try {
                        if (inputWithCommands.length != addBuildingArray.length) {
                            invalidInput();
                            continue;
                        }
                        System.out.println(roomBookingController.addBuilding(inputWithCommands[1], inputWithCommands[2]));
                    } catch (Exception e) {
                        System.out.println(e);
                        continue;
                    }
                } else if (inputWithCommands[0].equals("Remove-building")) {
                    if (inputWithCommands.length != removeBuildingArray.length) {
                        invalidInput();
                        continue;
                    }
                    System.out.println(roomBookingController.removeBuilding(inputWithCommands[1]));
                } else if (inputWithCommands[0].equals("Add-room")) {
                    try {
                        if (inputWithCommands.length != addRoomArray.length) {
                            invalidInput();
                            continue;
                        }
                        System.out.println(roomBookingController.addRoom(inputWithCommands[1], inputWithCommands[2]));
                    } catch (Exception e) {
                        System.out.println(e);
                        continue;
                    }
                } else if (inputWithCommands[0].equals("Remove-room")) {
                    try {
                        if (inputWithCommands.length != removeRoomArray.length) {
                            invalidInput();
                            continue;
                        }
                        System.out.println(roomBookingController.removeRoom(inputWithCommands[1], inputWithCommands[2]));
                    } catch (Exception e) {
                        System.out.println(e);
                        continue;
                    }
                } else if (inputWithCommands[0].equals("Book-room")) {
                    if (inputWithCommands.length != bookRoomArray.length) {
                        invalidInput();
                        continue;
                    }
                    System.out.println(roomBookingController.bookARoom(inputWithCommands[1], inputWithCommands[2], inputWithCommands[3], inputWithCommands[4], inputWithCommands[5]));
                } else if (inputWithCommands[0].equals("Cancel-booking")) {
                    if (inputWithCommands.length != cancelRoomArray.length) {
                        invalidInput();
                        continue;
                    }
                    System.out.println(roomBookingController.cancelARoom(inputWithCommands[1], inputWithCommands[2], inputWithCommands[3], inputWithCommands[4], inputWithCommands[5]));
                } else if (inputWithCommands[0].equals("Rooms-given-time")) {
                    if (inputWithCommands.length != roomsGivenTimeArray.length) {
                        invalidInput();
                        continue;
                    }
                    System.out.println(roomBookingController.chooseTimeAndSeeAllRoomsAvailable(inputWithCommands[1], inputWithCommands[2]));
                } else if (inputWithCommands[0].equals("Rooms-slot")) {
                    if (inputWithCommands.length != roomsGivenSlotArray.length) {
                        invalidInput();
                        continue;
                    }
                    System.out.println(roomBookingController.roomsAvailableInGivenTimeSlot(inputWithCommands[1], inputWithCommands[2], inputWithCommands[3]));
                } else if (inputWithCommands[0].equals("Schedule-of-a-room")) {
                    if (inputWithCommands.length != scheduleOfARoomArray.length) {
                        invalidInput();
                        continue;
                    }
                    System.out.println(roomBookingController.scheduleOfARoom(inputWithCommands[1], inputWithCommands[2]));
                } else if (inputWithCommands[0].equals("Booking-by-a-person")) {
                    if (inputWithCommands.length != bookingByAPersonArray.length) {
                        invalidInput();
                        continue;
                    }
                    System.out.println(roomBookingController.bookingsByAPerson(inputWithCommands[1], inputWithCommands[2]));
                } else if (inputWithCommands[0].equals("Save")) {
                    if (inputWithCommands.length != saveArray.length) {
                        invalidInput();
                        continue;
                    }
                    try {
                        System.out.println(roomBookingController.saveAll());
                    } catch (Exception e) {
                        System.out.println(e);
                        continue;
                    }
                } else if (inputWithCommands[0].equals("Load")) {
                    try {
                        if (inputWithCommands.length != loadArray.length) {
                            invalidInput();
                            continue;
                        }
                        System.out.println(roomBookingController.loadAll());
                    } catch (Exception e) {
                        System.out.println(e);
                        continue;
                    }
                } else if (inputWithCommands[0].equals("Quit-cl-ui")) {
                    if (inputWithCommands.length != quitArray.length) {
                        invalidInput();
                        continue;
                    }
                    closeClUi = true;
                    System.out.println("Exiting the Room Booking System Command Line Interface.");
                    quit = true;
                } else {
                    System.out.println("No command found. Please refer to the list of commands and try again.");
                }
            }
        }
    }

    /**
     * method called when any invalid input is given by the user
     */
    public void invalidInput() {
        System.out.println("Invalid input. Please type 'Help' for the list of acceptable commands.");
    }
}
