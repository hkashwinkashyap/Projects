package model;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test cases for the room booking model
 */
class UniversityRoomBookingModelTest {
    /**
     * test cases for checking the getter of the building names
     */
    @Test
    void getAllBuildingNames() {
        UniversityRoomBookingModel universityRoomBookingModel = new UniversityRoomBookingModel();
        try {
            String result = universityRoomBookingModel.addBuilding("Nisbet", "DRA");
            assertEquals("Great! Some of the University's real estate has been utilised to accommodate this new building. Thanks for the pollution you've created for that.", result);
            assertTrue(universityRoomBookingModel.getAllBuildingNames().contains("Nisbet"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * all cases involving adding a person
     */
    @Test
    void addPerson() {
        UniversityRoomBookingModel universityRoomBookingModel = new UniversityRoomBookingModel();
        try {
            assertEquals("Nice! You added this Person to the University successfully!", universityRoomBookingModel.addPerson("Parthu", "nandu@athadu.com"));
            assertEquals("This Email id already exists in the university. Try with a new one.", universityRoomBookingModel.addPerson("Parthu", "nandu@athadu.com"));
            assertEquals("Given name has a digit in it. Please try again without any digits.", universityRoomBookingModel.addPerson("h40s", "ok@something.com"));
            Exception exception = assertThrows(NameHasDigitException.class, (Executable) new Person("h40s", "ok@something.com"));
            assertTrue(exception.getMessage().equals("Given name has a digit in it. Please try again without any digits."));
            assertEquals("Invalid email address! Please try again.", universityRoomBookingModel.addPerson("Ok", "nim9032jd"));
            Exception exception1 = assertThrows(InvalidEmailException.class, (Executable) new Person("Ok", "nim9032jd"));
            assertTrue(exception.getMessage().equals("Invalid email address! Please try again."));
            assertEquals(1, universityRoomBookingModel.getPeopleInUniversity().size());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * test cases which have removing a person in wide scenarios
     */
    @Test
    void removePerson() {
        UniversityRoomBookingModel universityRoomBookingModel = new UniversityRoomBookingModel();
        try {
            assertEquals("Nice! You added this Person to the University successfully!", universityRoomBookingModel.addPerson("Parthu", "nandu@athadu.com"));
            assertEquals("Haha! This Person has been removed from the University.", universityRoomBookingModel.removePerson("nandu@athadu.com"));
            assertEquals("Nice! You added this Person to the University successfully!", universityRoomBookingModel.addPerson("Parthu", "nandu@athadu.com"));
            assertEquals("No person with that email and name exists to remove.", universityRoomBookingModel.removePerson("Nandu@athadu.com"));
            universityRoomBookingModel.addBuilding("Nisbet", "DRA");
            universityRoomBookingModel.addOrRemoveARoom(1, "12", "Nisbet");
            assertEquals("Aha! Booked the room for 26-12-2022 from 10:0 to 11:0.", universityRoomBookingModel.bookOrCancelARoomByAPerson(1, "nandu@athadu.com", "26-12-2022", "12", "10:0", "11:0"));
            assertEquals("Haha! This Person has been removed from the University. All the bookings made by this person are also cancelled.", universityRoomBookingModel.removePerson("nandu@athadu.com"));
            assertEquals("The following rooms are available for given time slot:\n" + "12\n", universityRoomBookingModel.roomsAvailableInTheGivenTimeSlot("10:0", "11:0", "26-12-2022"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * all cases involving adding a building
     */
    @Test
    void addBuilding() {
        UniversityRoomBookingModel universityRoomBookingModel = new UniversityRoomBookingModel();
        try {
            assertEquals("Great! Some of the University's real estate has been utilised to accommodate this new building. Thanks for the pollution you've created for that.", universityRoomBookingModel.addBuilding("Nisbet", "DRA"));
            assertEquals("Building name already exists. Try with a new name.", universityRoomBookingModel.addBuilding("Nisbet", "DRA"));
            Exception exception = assertThrows(NameAlreadyExistsException.class, (Executable) new Building("Nisbet", "DRA"));
            assertTrue(exception.getMessage().equals("Building name already exists. Try with a new name."));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * all cases involving removing a building
     */
    @Test
    void removeBuilding() {
        UniversityRoomBookingModel universityRoomBookingModel = new UniversityRoomBookingModel();
        try {
            universityRoomBookingModel.addBuilding("Nisbet", "DRA");
        } catch (Exception e) {
            System.out.println(e);
        }
        assertEquals("Haha! This Building has been removed from the University. More pollution in destructing a building. Plant some trees in that place at-least!", universityRoomBookingModel.removeBuilding("Nisbet"));
        assertEquals("No buildings are there to remove.", universityRoomBookingModel.removeBuilding("Nisbet"));
    }

    /**
     * all cases involving adding and removing a room
     */
    @Test
    void addOrRemoveARoom() {
        UniversityRoomBookingModel universityRoomBookingModel = new UniversityRoomBookingModel();
        try {
            assertEquals("Building with that name doesn't exist. Add a building first and then try.", universityRoomBookingModel.addOrRemoveARoom(1, "12", "Nisbet"));
            Exception exception = assertThrows(RoomWithoutBuildingException.class, (Executable) new Room("12", "Nisbet"));
            assertTrue(exception.getMessage().equals("Building with that name doesn't exist. Add a building first and then try."));
            assertEquals("Room with name 12 created and added to the building with name Nisbet.", universityRoomBookingModel.addOrRemoveARoom(1, "12", "Nisbet"));
            assertEquals("Room name already exists. Try with a new name.", universityRoomBookingModel.addOrRemoveARoom(1, "12", "Nisbet"));
            assertEquals("Removed the room. Cheers!", universityRoomBookingModel.addOrRemoveARoom(2, "12", "Nisbet"));
            assertEquals("No room exists with that name to remove.", universityRoomBookingModel.addOrRemoveARoom(2, "12", "Nisbet"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * all cases involving booking and cancelling a booking done by a person
     */
    @Test
    void bookOrCancelARoomByAPerson() {
        UniversityRoomBookingModel universityRoomBookingModel = new UniversityRoomBookingModel();
        try {
            assertEquals("Haha! Add some people in the University and then try.", universityRoomBookingModel.bookOrCancelARoomByAPerson(1, "nandu@athadu.com", "26-12-2022", "12", "10:00", "10:30"));
            universityRoomBookingModel.addPerson("Parthu", "nandu@athadu.com");
            assertEquals("Oh no! No person with that name and email exists in the University. Try once you've added this person to book the room.", universityRoomBookingModel.bookOrCancelARoomByAPerson(1, "evado@ok.com", "26-12-2022", "12", "10:00", "10:30"));
            assertEquals("Well! You gotta have a building, and it must have this room first to book it.", universityRoomBookingModel.bookOrCancelARoomByAPerson(1, "nandu@athadu.com", "26-12-2022", "12", "10:00", "10:30"));
            universityRoomBookingModel.addBuilding("Nisbet", "DRA");
            assertEquals("The room doesn't exist so try adding a room first and then try.", universityRoomBookingModel.bookOrCancelARoomByAPerson(1, "nandu@athadu.com", "26-12-2022", "12", "10:00", "10:30"));
            universityRoomBookingModel.addOrRemoveARoom(1, "12", "Nisbet");
            assertEquals("Aha! Booked the room for 26-12-2022 from 10:00 to 10:30.", universityRoomBookingModel.bookOrCancelARoomByAPerson(1, "nandu@athadu.com", "26-12-2022", "12", "10:00", "10:30"));
            assertEquals("Booking un-successful, try again. All unavailable slots are: 10:5\n10:10\n10:15\n10:20\n10:25\n10:30\n", universityRoomBookingModel.bookOrCancelARoomByAPerson(1, "nandu@athadu.com", "26-12-2022", "12", "9:55", "10:30"));
            assertEquals("Booking un-successful, try again. Please enter the minutes in multiples of 5.", universityRoomBookingModel.bookOrCancelARoomByAPerson(1, "nandu@athadu.com", "26-12-2022", "12", "10:2", "10:10"));
            assertEquals("Booking un-successful, try again. Invalid time slots, ending time cannot be before the starting time.", universityRoomBookingModel.bookOrCancelARoomByAPerson(1, "nandu@athadu.com", "26-12-2022", "12", "10:0", "9:10"));
            assertEquals("Booking un-successful, try again. Cannot book or cancel from 23:55 to 0:0 slot as it is across multiple dates.", universityRoomBookingModel.bookOrCancelARoomByAPerson(1, "nandu@athadu.com", "26-12-2022", "12", "23:0", "0:0"));
            assertEquals("Booking un-successful, try again. Oh no! You have not entered the time format as required. Try with the acceptable format as h:m.", universityRoomBookingModel.bookOrCancelARoomByAPerson(1, "nandu@athadu.com", "26-12-2022", "12", "23.0", "00/0"));
            assertEquals("Booking un-successful, try again. Enter a valid date which should be in dd-mm-yyyy and greater than current date.", universityRoomBookingModel.bookOrCancelARoomByAPerson(1, "nandu@athadu.com", "26/12dc-2022", "12", "10:2", "10:10"));
            assertEquals("Okay! Cancelled the booked the room from 10:00 to 10:30.", universityRoomBookingModel.bookOrCancelARoomByAPerson(2, "nandu@athadu.com", "26-12-2022", "12", "10:00", "10:30"));
            universityRoomBookingModel.bookOrCancelARoomByAPerson(1, "nandu@athadu.com", "26-12-2022", "12", "10:5", "10:20");
            assertEquals("This time slot is part another booking as a subset or superset so cannot process your request.", universityRoomBookingModel.bookOrCancelARoomByAPerson(2, "nandu@athadu.com", "26-12-2022", "12", "10:5", "10:30"));
            assertEquals("This time slot is part another booking as a subset or superset so cannot process your request.", universityRoomBookingModel.bookOrCancelARoomByAPerson(2, "nandu@athadu.com", "26-12-2022", "12", "10:5", "10:15"));
            universityRoomBookingModel.bookOrCancelARoomByAPerson(2, "nandu@athadu.com", "26-12-2022", "12", "10:5", "10:20");
            assertEquals("No booking in the given slot to cancel.", universityRoomBookingModel.bookOrCancelARoomByAPerson(2, "nandu@athadu.com", "26-12-2022", "12", "10:5", "10:20"));
            assertEquals("No booking on the given date to cancel.", universityRoomBookingModel.bookOrCancelARoomByAPerson(2, "nandu@athadu.com", "28-12-2022", "12", "10:5", "10:20"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * all cases involving the functionality to view all the rooms available at a given time and date
     */
    @Test
    void roomsAvailableAtGivenTime() {
        try {
            UniversityRoomBookingModel universityRoomBookingModel = new UniversityRoomBookingModel();
            assertEquals("No Buildings are there which means no rooms also, so nothing to show. Add them first.", universityRoomBookingModel.roomsAvailableAtGivenTime("10:0", "28-12-2022"));
            universityRoomBookingModel.addBuilding("Nisbet", "DRA");
            assertEquals("The following rooms are available for given time to 5 minutes from that:\n", universityRoomBookingModel.roomsAvailableAtGivenTime("10:0", "28-12-2022"));
            universityRoomBookingModel.addOrRemoveARoom(1, "12", "Nisbet");
            universityRoomBookingModel.addPerson("Parthu", "nandu@athadu.com");
            assertEquals("The following rooms are available for given time to 5 minutes from that:\n" +
                    "12\n", universityRoomBookingModel.roomsAvailableAtGivenTime("10:0", "28-12-2022"));
            assertEquals("Please give the minutes in units of 5.", universityRoomBookingModel.roomsAvailableAtGivenTime("10:2", "28-12-2022"));
            assertEquals("Can't make booking at that time as it spreads across multiple dates.", universityRoomBookingModel.roomsAvailableAtGivenTime("23:55", "28-12-2022"));
            assertEquals("Invalid date, try again.", universityRoomBookingModel.roomsAvailableAtGivenTime("23:55", "28ok-12-2022"));
            assertEquals("Oh no! You have not entered the time format as required. Try with the acceptable format.", universityRoomBookingModel.roomsAvailableAtGivenTime("10.0", "28-12-2022"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * all cases involving the functionality to view all the rooms available in a given time slot and date
     */
    @Test
    void roomsAvailableInTheGivenTimeSlot() {
        try {
            UniversityRoomBookingModel universityRoomBookingModel = new UniversityRoomBookingModel();
            assertEquals("Please give the minutes in units of 5.", universityRoomBookingModel.roomsAvailableInTheGivenTimeSlot("10:03", "10:30", "28-12-2022"));
            assertEquals("No Buildings are there which means no rooms also, so nothing to show. Add them first.", universityRoomBookingModel.roomsAvailableInTheGivenTimeSlot("10:05", "10:30", "28-12-2022"));
            universityRoomBookingModel.addBuilding("Nisbet", "DRA");
            assertEquals("Invalid date, try again.", universityRoomBookingModel.roomsAvailableInTheGivenTimeSlot("10:03", "10:30", "28=-2-2022"));
            universityRoomBookingModel.addOrRemoveARoom(1, "12", "Nisbet");
            assertEquals("The following rooms are available for given time slot:\n" +
                    "12\n", universityRoomBookingModel.roomsAvailableInTheGivenTimeSlot("10:0", "10:30", "28-12-2022"));
            assertEquals("Oh no! You have not entered the time format as required. Try with the acceptable format.", universityRoomBookingModel.roomsAvailableInTheGivenTimeSlot("10.", "10:30", "28-12-2022"));
            assertEquals("Can't make booking at that time, as it spreads across multiple dates.", universityRoomBookingModel.roomsAvailableInTheGivenTimeSlot("23:0", "0:0", "28-12-2022"));
            universityRoomBookingModel.addPerson("Parthu", "nandu@athadu.com");
            universityRoomBookingModel.bookOrCancelARoomByAPerson(1, "nandu@athadu.com", "28-12-2022", "12", "10:0", "12:0");
            assertEquals("No rooms are available for the entire time slot given.", universityRoomBookingModel.roomsAvailableInTheGivenTimeSlot("10:05", "10:30", "28-12-2022"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * all cases involving the functionality to view all the rooms booked by a person
     */
    @Test
    void viewAllBookingsByAPerson() {
        try {
            UniversityRoomBookingModel universityRoomBookingModel = new UniversityRoomBookingModel();
            assertEquals("Add few people and then try.", universityRoomBookingModel.viewAllBookingsByAPerson("Parthu", "nandu@athadu.com"));
            universityRoomBookingModel.addPerson("Parthu", "nandu@athadu.com");
            assertEquals("This person in not part of the university. Add first and then try.", universityRoomBookingModel.viewAllBookingsByAPerson("Parthu", "parthu@athadu.com"));
            universityRoomBookingModel.addBuilding("Nisbet", "DRA");
            universityRoomBookingModel.addOrRemoveARoom(1, "12", "Nisbet");
            universityRoomBookingModel.bookOrCancelARoomByAPerson(1, "nandu@athadu.com", "28-12-2022", "12", "10:0", "12:0");
            assertEquals("Bookings made by Parthu:Booked 12 for 28-12-2022 from 10:0 to 12:0\n", universityRoomBookingModel.viewAllBookingsByAPerson("Parthu", "nandu@athadu.com"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * all cases involving the functionality to view schedule of a given room
     */
    @Test
    void viewScheduleOfARoom() {
        try {
            UniversityRoomBookingModel universityRoomBookingModel = new UniversityRoomBookingModel();
            assertEquals("No buildings are there. Add few.", universityRoomBookingModel.viewScheduleOfARoom("12", "28-12-2022"));
            universityRoomBookingModel.addBuilding("Nisbet", "DRA");
            universityRoomBookingModel.addOrRemoveARoom(1, "12", "Nisbet");
            assertEquals("The room is available all day on 28-12-2022.", universityRoomBookingModel.viewScheduleOfARoom("12", "28-12-2022"));
            universityRoomBookingModel.addPerson("Parthu", "nandu@athadu.com");
            universityRoomBookingModel.bookOrCancelARoomByAPerson(1, "nandu@athadu.com", "28-12-2022", "12", "0:0", "0:10");
            assertEquals("The availability of slots are indicated by its roof time.\n" +
                    "Meaning if the room is booked from 10:00 to 10:05, \n" +
                    "it shows that 10:05 is Booked but not 10:00\n" +
                    "indicating the room is booked from 10:00 to 10:05.\n" +
                    "0:0 - Available\n" +
                    "0:5 - Booked\n" +
                    "0:10 - Booked\n" +
                    "0:15 - Available\n" +
                    "0:20 - Available\n" +
                    "0:25 - Available\n" +
                    "0:30 - Available\n" +
                    "0:35 - Available\n" +
                    "0:40 - Available\n" +
                    "0:45 - Available\n" +
                    "0:50 - Available\n" +
                    "0:55 - Available\n" +
                    "1:0 - Available\n" +
                    "1:5 - Available\n" +
                    "1:10 - Available\n" +
                    "1:15 - Available\n" +
                    "1:20 - Available\n" +
                    "1:25 - Available\n" +
                    "1:30 - Available\n" +
                    "1:35 - Available\n" +
                    "1:40 - Available\n" +
                    "1:45 - Available\n" +
                    "1:50 - Available\n" +
                    "1:55 - Available\n" +
                    "2:0 - Available\n" +
                    "2:5 - Available\n" +
                    "2:10 - Available\n" +
                    "2:15 - Available\n" +
                    "2:20 - Available\n" +
                    "2:25 - Available\n" +
                    "2:30 - Available\n" +
                    "2:35 - Available\n" +
                    "2:40 - Available\n" +
                    "2:45 - Available\n" +
                    "2:50 - Available\n" +
                    "2:55 - Available\n" +
                    "3:0 - Available\n" +
                    "3:5 - Available\n" +
                    "3:10 - Available\n" +
                    "3:15 - Available\n" +
                    "3:20 - Available\n" +
                    "3:25 - Available\n" +
                    "3:30 - Available\n" +
                    "3:35 - Available\n" +
                    "3:40 - Available\n" +
                    "3:45 - Available\n" +
                    "3:50 - Available\n" +
                    "3:55 - Available\n" +
                    "4:0 - Available\n" +
                    "4:5 - Available\n" +
                    "4:10 - Available\n" +
                    "4:15 - Available\n" +
                    "4:20 - Available\n" +
                    "4:25 - Available\n" +
                    "4:30 - Available\n" +
                    "4:35 - Available\n" +
                    "4:40 - Available\n" +
                    "4:45 - Available\n" +
                    "4:50 - Available\n" +
                    "4:55 - Available\n" +
                    "5:0 - Available\n" +
                    "5:5 - Available\n" +
                    "5:10 - Available\n" +
                    "5:15 - Available\n" +
                    "5:20 - Available\n" +
                    "5:25 - Available\n" +
                    "5:30 - Available\n" +
                    "5:35 - Available\n" +
                    "5:40 - Available\n" +
                    "5:45 - Available\n" +
                    "5:50 - Available\n" +
                    "5:55 - Available\n" +
                    "6:0 - Available\n" +
                    "6:5 - Available\n" +
                    "6:10 - Available\n" +
                    "6:15 - Available\n" +
                    "6:20 - Available\n" +
                    "6:25 - Available\n" +
                    "6:30 - Available\n" +
                    "6:35 - Available\n" +
                    "6:40 - Available\n" +
                    "6:45 - Available\n" +
                    "6:50 - Available\n" +
                    "6:55 - Available\n" +
                    "7:0 - Available\n" +
                    "7:5 - Available\n" +
                    "7:10 - Available\n" +
                    "7:15 - Available\n" +
                    "7:20 - Available\n" +
                    "7:25 - Available\n" +
                    "7:30 - Available\n" +
                    "7:35 - Available\n" +
                    "7:40 - Available\n" +
                    "7:45 - Available\n" +
                    "7:50 - Available\n" +
                    "7:55 - Available\n" +
                    "8:0 - Available\n" +
                    "8:5 - Available\n" +
                    "8:10 - Available\n" +
                    "8:15 - Available\n" +
                    "8:20 - Available\n" +
                    "8:25 - Available\n" +
                    "8:30 - Available\n" +
                    "8:35 - Available\n" +
                    "8:40 - Available\n" +
                    "8:45 - Available\n" +
                    "8:50 - Available\n" +
                    "8:55 - Available\n" +
                    "9:0 - Available\n" +
                    "9:5 - Available\n" +
                    "9:10 - Available\n" +
                    "9:15 - Available\n" +
                    "9:20 - Available\n" +
                    "9:25 - Available\n" +
                    "9:30 - Available\n" +
                    "9:35 - Available\n" +
                    "9:40 - Available\n" +
                    "9:45 - Available\n" +
                    "9:50 - Available\n" +
                    "9:55 - Available\n" +
                    "10:0 - Available\n" +
                    "10:5 - Available\n" +
                    "10:10 - Available\n" +
                    "10:15 - Available\n" +
                    "10:20 - Available\n" +
                    "10:25 - Available\n" +
                    "10:30 - Available\n" +
                    "10:35 - Available\n" +
                    "10:40 - Available\n" +
                    "10:45 - Available\n" +
                    "10:50 - Available\n" +
                    "10:55 - Available\n" +
                    "11:0 - Available\n" +
                    "11:5 - Available\n" +
                    "11:10 - Available\n" +
                    "11:15 - Available\n" +
                    "11:20 - Available\n" +
                    "11:25 - Available\n" +
                    "11:30 - Available\n" +
                    "11:35 - Available\n" +
                    "11:40 - Available\n" +
                    "11:45 - Available\n" +
                    "11:50 - Available\n" +
                    "11:55 - Available\n" +
                    "12:0 - Available\n" +
                    "12:5 - Available\n" +
                    "12:10 - Available\n" +
                    "12:15 - Available\n" +
                    "12:20 - Available\n" +
                    "12:25 - Available\n" +
                    "12:30 - Available\n" +
                    "12:35 - Available\n" +
                    "12:40 - Available\n" +
                    "12:45 - Available\n" +
                    "12:50 - Available\n" +
                    "12:55 - Available\n" +
                    "13:0 - Available\n" +
                    "13:5 - Available\n" +
                    "13:10 - Available\n" +
                    "13:15 - Available\n" +
                    "13:20 - Available\n" +
                    "13:25 - Available\n" +
                    "13:30 - Available\n" +
                    "13:35 - Available\n" +
                    "13:40 - Available\n" +
                    "13:45 - Available\n" +
                    "13:50 - Available\n" +
                    "13:55 - Available\n" +
                    "14:0 - Available\n" +
                    "14:5 - Available\n" +
                    "14:10 - Available\n" +
                    "14:15 - Available\n" +
                    "14:20 - Available\n" +
                    "14:25 - Available\n" +
                    "14:30 - Available\n" +
                    "14:35 - Available\n" +
                    "14:40 - Available\n" +
                    "14:45 - Available\n" +
                    "14:50 - Available\n" +
                    "14:55 - Available\n" +
                    "15:0 - Available\n" +
                    "15:5 - Available\n" +
                    "15:10 - Available\n" +
                    "15:15 - Available\n" +
                    "15:20 - Available\n" +
                    "15:25 - Available\n" +
                    "15:30 - Available\n" +
                    "15:35 - Available\n" +
                    "15:40 - Available\n" +
                    "15:45 - Available\n" +
                    "15:50 - Available\n" +
                    "15:55 - Available\n" +
                    "16:0 - Available\n" +
                    "16:5 - Available\n" +
                    "16:10 - Available\n" +
                    "16:15 - Available\n" +
                    "16:20 - Available\n" +
                    "16:25 - Available\n" +
                    "16:30 - Available\n" +
                    "16:35 - Available\n" +
                    "16:40 - Available\n" +
                    "16:45 - Available\n" +
                    "16:50 - Available\n" +
                    "16:55 - Available\n" +
                    "17:0 - Available\n" +
                    "17:5 - Available\n" +
                    "17:10 - Available\n" +
                    "17:15 - Available\n" +
                    "17:20 - Available\n" +
                    "17:25 - Available\n" +
                    "17:30 - Available\n" +
                    "17:35 - Available\n" +
                    "17:40 - Available\n" +
                    "17:45 - Available\n" +
                    "17:50 - Available\n" +
                    "17:55 - Available\n" +
                    "18:0 - Available\n" +
                    "18:5 - Available\n" +
                    "18:10 - Available\n" +
                    "18:15 - Available\n" +
                    "18:20 - Available\n" +
                    "18:25 - Available\n" +
                    "18:30 - Available\n" +
                    "18:35 - Available\n" +
                    "18:40 - Available\n" +
                    "18:45 - Available\n" +
                    "18:50 - Available\n" +
                    "18:55 - Available\n" +
                    "19:0 - Available\n" +
                    "19:5 - Available\n" +
                    "19:10 - Available\n" +
                    "19:15 - Available\n" +
                    "19:20 - Available\n" +
                    "19:25 - Available\n" +
                    "19:30 - Available\n" +
                    "19:35 - Available\n" +
                    "19:40 - Available\n" +
                    "19:45 - Available\n" +
                    "19:50 - Available\n" +
                    "19:55 - Available\n" +
                    "20:0 - Available\n" +
                    "20:5 - Available\n" +
                    "20:10 - Available\n" +
                    "20:15 - Available\n" +
                    "20:20 - Available\n" +
                    "20:25 - Available\n" +
                    "20:30 - Available\n" +
                    "20:35 - Available\n" +
                    "20:40 - Available\n" +
                    "20:45 - Available\n" +
                    "20:50 - Available\n" +
                    "20:55 - Available\n" +
                    "21:0 - Available\n" +
                    "21:5 - Available\n" +
                    "21:10 - Available\n" +
                    "21:15 - Available\n" +
                    "21:20 - Available\n" +
                    "21:25 - Available\n" +
                    "21:30 - Available\n" +
                    "21:35 - Available\n" +
                    "21:40 - Available\n" +
                    "21:45 - Available\n" +
                    "21:50 - Available\n" +
                    "21:55 - Available\n" +
                    "22:0 - Available\n" +
                    "22:5 - Available\n" +
                    "22:10 - Available\n" +
                    "22:15 - Available\n" +
                    "22:20 - Available\n" +
                    "22:25 - Available\n" +
                    "22:30 - Available\n" +
                    "22:35 - Available\n" +
                    "22:40 - Available\n" +
                    "22:45 - Available\n" +
                    "22:50 - Available\n" +
                    "22:55 - Available\n" +
                    "23:0 - Available\n" +
                    "23:5 - Available\n" +
                    "23:10 - Available\n" +
                    "23:15 - Available\n" +
                    "23:20 - Available\n" +
                    "23:25 - Available\n" +
                    "23:30 - Available\n" +
                    "23:35 - Available\n" +
                    "23:40 - Available\n" +
                    "23:45 - Available\n" +
                    "23:50 - Available\n" +
                    "23:55 - Available\n", universityRoomBookingModel.viewScheduleOfARoom("12", "28-12-2022"));
            assertEquals("Invalid date or format. Try 'dd-mm-yyyy'.", universityRoomBookingModel.viewScheduleOfARoom("12", "28-12sd-2022"));
            universityRoomBookingModel.addOrRemoveARoom(2, "12", "Nisbet");
            assertEquals("No room exists with that name.", universityRoomBookingModel.viewScheduleOfARoom("12", "28-12-2022"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * all cases involving the functionality to view all the transactions done for a particular room
     */
    @Test
    void listAllBookings() {
        try {
            UniversityRoomBookingModel universityRoomBookingModel = new UniversityRoomBookingModel();
            assertEquals("No buildings are there. Add few.", universityRoomBookingModel.listAllBookings("12"));
            universityRoomBookingModel.addBuilding("Nisbet", "DRA");
            assertEquals("No room exits with that name. Add one and then try.", universityRoomBookingModel.listAllBookings("12"));
            universityRoomBookingModel.addOrRemoveARoom(1, "12", "Nisbet");
            assertEquals("List of all the bookings for 12:\n" +
                    "{}", universityRoomBookingModel.listAllBookings("12"));
            universityRoomBookingModel.addPerson("Parthu", "nandu@athadu.com");
            universityRoomBookingModel.bookOrCancelARoomByAPerson(1, "nandu@athadu.com", "26-12-2022", "12", "10:5", "10:20");
            assertEquals("List of all the bookings for 12:\n" +
                    "{nandu@athadu.com=\n" +
                    "Booked for 26-12-2022 from 10:5 to 10:20}", universityRoomBookingModel.listAllBookings("12"));
            universityRoomBookingModel.bookOrCancelARoomByAPerson(2, "nandu@athadu.com", "26-12-2022", "12", "10:5", "10:20");
            assertEquals("List of all the bookings for 12:\n" +
                    "{nandu@athadu.com=\n" +
                    "Booked for 26-12-2022 from 10:5 to 10:20. And then Cancelled the booking for 26-12-2022 from 10:5 to 10:20}", universityRoomBookingModel.listAllBookings("12"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
