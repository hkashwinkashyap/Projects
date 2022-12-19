package controller;

import model.UniversityRoomBookingModel;

import java.io.*;

/**
 * Controller class of the system
 */
public class RoomBookingController implements Serializable {
    private UniversityRoomBookingModel universityRoomBookingModel;

    /**
     * constructor method takes in instance of unified model of the room booking system
     *
     * @param universityRoomBookingModel instance of the model of the system
     */
    public RoomBookingController(UniversityRoomBookingModel universityRoomBookingModel) {
        this.universityRoomBookingModel = universityRoomBookingModel;
    }

    /**
     * help method shows the list commands required to be given by the user and is synchronised
     *
     * @return string of list of commands
     */
    public synchronized String help() {
        return (" Enter the time as 'h:m' for example '6:0'. No need of extra preceding zero like '06:00'.\n 1. To add a person : Add-person <personName> <emailAddress>\n 2. To remove a person : Remove-person <emailAddressOfThePerson>\n 3. To add a building : Add-building <buildingName> <buildingAddress>\n 4. To remove a building : Remove-building <buildingName>\n 5. To add a room : Add-room <roomName> <buildingName>\n 6. To remove a room : Remove-room <roomName> <buildingName>\n 7. To make a booking : Book-room <personEmailAddress> <date in dd—mm—yyyy> <roomName> <startTimeOfTheBooking> <endTimeOfTheBooking>\n 8. To cancel a booking : Cancel-booking <personEmailAddressWhoMadeTheBooking> <date in dd—mm—yyyy> <roomName> <startTimeOfTheBooking> <endTimeOfTheBooking>\n 9. To get all the rooms free at a given time : Rooms-given-time <timeRequired> <date in dd—mm—yyyy>\n 10. To get all the rooms free for the whole of given time slot : Rooms-slot <startTime> <endTime> <date in dd—mm—yyyy>\n 11. To get the schedule for a particular room : Schedule-of-a-room <roomName> <date in dd—mm—yyyy>\n 12. To get all the bookings done by a particular person : Booking-by-a-person <personName> <personEmail>\n 13. To save all the data : Save\n 14. To load from saved data : Load\n 15. To get the list of all transactions made against a particular room : List-of-all-bookings-for-a-room <roomName>\n");
    }

    /**
     * calls the add person of the model
     *
     * @param personName   string value of person name
     * @param emailAddress string value of the email id of the person
     * @return string value returned by the model
     * @throws Exception might throw invalid email address exception or digit in name exception
     */
    public synchronized String addPerson(String personName, String emailAddress) throws Exception {
        return (universityRoomBookingModel.addPerson(personName, emailAddress));
    }

    /**
     * calls remove person method in the model
     *
     * @param personEmail string value of the email address
     * @return string value returned by the model
     */
    public synchronized String removePerson(String personEmail) {
        return (universityRoomBookingModel.removePerson(personEmail));
    }

    /**
     * calls add building of the model
     *
     * @param buildingName    string value of the building name
     * @param buildingAddress string value of the building address
     * @return string value which says added building or not
     * @throws Exception throws name already exists exception
     */
    public synchronized String addBuilding(String buildingName, String buildingAddress) throws Exception {
        return (universityRoomBookingModel.addBuilding(buildingName, buildingAddress));
    }

    /**
     * calls remove building method in the model's instance
     *
     * @param buildingName string value of building name
     * @return string value returned by the model
     */
    public synchronized String removeBuilding(String buildingName) {
        return (universityRoomBookingModel.removeBuilding(buildingName));
    }

    /**
     * intimates the model to book a room with given input parameters
     *
     * @param personEmail string value of person's email address
     * @param date        string value of person's email address
     * @param roomName    string value of person's email address
     * @param startTime   string of the starting time
     * @param endTime     string of the ending time
     * @return string returned by the model's method
     */
    public synchronized String bookARoom(String personEmail, String date, String roomName, String startTime, String endTime) {
        return (universityRoomBookingModel.bookOrCancelARoomByAPerson(1, personEmail, date, roomName, startTime, endTime));
    }

    /**
     * calls the method to cancel a room booking method in the model
     *
     * @param personEmail string value of person's email address
     * @param date        string value of person's email address
     * @param roomName    string value of person's email address
     * @param startTime   string of the starting time
     * @param endTime     string of the ending time
     * @return string returned by the model's method
     */
    public synchronized String cancelARoom(String personEmail, String date, String roomName, String startTime, String endTime) {
        return (universityRoomBookingModel.bookOrCancelARoomByAPerson(2, personEmail, date, roomName, startTime, endTime));
    }

    /**
     * calls the method to add a room method in the model
     *
     * @param roomName     string of the room name
     * @param buildingName string of building name
     * @return string returned by the model's method
     * @throws Exception throws name already exists exception and room without building exception
     */
    public synchronized String addRoom(String roomName, String buildingName) throws Exception {
        return (universityRoomBookingModel.addOrRemoveARoom(1, roomName, buildingName));
    }

    /**
     * calls the method to remove a room method in the model
     *
     * @param roomName     string of the room name
     * @param buildingName string of building name
     * @return string returned by the model's method
     * @throws Exception throws name already exists exception and room without building exception
     */
    public synchronized String removeRoom(String roomName, String buildingName) throws Exception {
        return (universityRoomBookingModel.addOrRemoveARoom(2, roomName, buildingName));
    }

    /**
     * passes on the arguments to the relevant method in the model
     *
     * @param timeRequired string value of the time required to be searched
     * @param date         string of the date
     * @return string value which the model's method returns
     */
    public synchronized String chooseTimeAndSeeAllRoomsAvailable(String timeRequired, String date) {
        return (universityRoomBookingModel.roomsAvailableAtGivenTime(timeRequired, date));
    }

    /**
     * passes on the arguments to the relevant method in the model
     *
     * @param startTime string value of starting time
     * @param endTime   string value of the ending time
     * @param date      string of the date
     * @return string returned by model
     */
    public synchronized String roomsAvailableInGivenTimeSlot(String startTime, String endTime, String date) {
        return (universityRoomBookingModel.roomsAvailableInTheGivenTimeSlot(startTime, endTime, date));
    }

    /**
     * passes on the arguments to the relevant method in the model
     *
     * @param roomName string value of the name of the room
     * @param date     string value of the date
     * @return string returned by model
     */
    public synchronized String scheduleOfARoom(String roomName, String date) {
        return (universityRoomBookingModel.viewScheduleOfARoom(roomName, date));
    }

    /**
     * passes on the arguments to the relevant method in the model
     *
     * @param personName  string value of the person's name
     * @param personEmail string value of the person's email address
     * @return string returned by model
     */
    public synchronized String bookingsByAPerson(String personName, String personEmail) {
        return (universityRoomBookingModel.viewAllBookingsByAPerson(personName, personEmail));
    }

    /**
     * saves the instance in a txt file
     *
     * @return string whether the saving was successful or not
     * @throws IOException thrown by the stream
     */
    public synchronized String saveAll() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(new File("universityRoomBookingModel.txt"));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this.universityRoomBookingModel);
        objectOutputStream.close();
        return ("Saved.");
    }

    /**
     * loads the instance from the txt file with the specified name
     *
     * @return string whether the load was successful or not
     * @throws IOException            thrown by the stream
     * @throws ClassNotFoundException thrown by the stream
     */
    public synchronized String loadAll() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File("universityRoomBookingModel.txt"));
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        this.universityRoomBookingModel = (UniversityRoomBookingModel) objectInputStream.readObject();
        return ("Loaded.");
    }

    /**
     * passes on the arguments to the relevant method in the model
     *
     * @param roomName string value of the room name
     * @return string returned by model
     */
    public synchronized String listOfAllBookingForARoom(String roomName) {
        return (universityRoomBookingModel.listAllBookings(roomName));
    }
}
