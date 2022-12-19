package view.gui;

import controller.RoomBookingController;
import model.UniversityRoomBookingModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * class for the booking and cancelling gui of the system
 */
public class BookingGui extends JFrame implements ActionListener {
    private RoomBookingController roomBookingController;
    private UniversityRoomBookingModel universityRoomBookingModel;
    private JButton bookARoom, cancelABooking;
    private JTextField roomName, personEmail, startTime, endTime, date;
    private JLabel roomNameLabel, personEmailLabel, startTimeLabel, endTimeLabel, dateLabel;
    private JLabel commandHelp1, commandHelp2, commandHelp3;

    /**
     * constructor method which also sets up the jframe
     *
     * @param roomBookingController      instance of the controller
     * @param universityRoomBookingModel object of the model
     */
    public BookingGui(RoomBookingController roomBookingController, UniversityRoomBookingModel universityRoomBookingModel) {
        super("Book or Cancel a Room");
        this.roomBookingController = roomBookingController;
        this.universityRoomBookingModel = universityRoomBookingModel;
        bookARoom = new JButton("Book a Room");
        cancelABooking = new JButton("Cancel a Booking");
        roomName = new JTextField(20);
        personEmail = new JTextField(30);
        date = new JTextField(15);
        startTime = new JTextField(10);
        endTime = new JTextField(10);
        roomNameLabel = new JLabel("Room name");
        personEmailLabel = new JLabel("Person email address");
        dateLabel = new JLabel("Date");
        startTimeLabel = new JLabel("Starting time");
        endTimeLabel = new JLabel("Ending time");
        commandHelp1 = new JLabel("Type these commands in the input box and press subsequent button.\n");
        commandHelp2 = new JLabel("To make a booking : <personEmailAddress> <date in dd—mm—yyyy> <roomName> <startTimeOfTheBooking> <endTimeOfTheBooking>\n");
        commandHelp3 = new JLabel("To cancel a booking : <personEmailAddressWhoMadeTheBooking> <date in dd—mm—yyyy> <roomName> <startTimeOfTheBooking> <endTimeOfTheBooking>");
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(commandHelp1);
        add(commandHelp2);
        add(commandHelp3);
        add(roomNameLabel);
        add(roomName);
        add(personEmailLabel);
        add(personEmail);
        add(dateLabel);
        add(date);
        add(startTimeLabel);
        add(startTime);
        add(endTimeLabel);
        add(endTime);
        add(bookARoom);
        add(cancelABooking);

        bookARoom.addActionListener(this);
        cancelABooking.addActionListener(this);

        setSize(1100, 200);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * listens for the event and responds appropriately
     *
     * @param e event type which has the source information
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookARoom) {
            try {
                JOptionPane.showMessageDialog(bookARoom, roomBookingController.bookARoom(personEmail.getText(), date.getText(), roomName.getText(), startTime.getText(), endTime.getText()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(bookARoom, ex);
            }
        } else if (e.getSource() == cancelABooking) {
            JOptionPane.showMessageDialog(cancelABooking, roomBookingController.cancelARoom(personEmail.getText(), date.getText(), roomName.getText(), startTime.getText(), endTime.getText()));
        }
    }
}
