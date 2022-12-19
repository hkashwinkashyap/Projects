package view.gui;

import controller.RoomBookingController;
import model.UniversityRoomBookingModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * class for bookings done by a person gui
 */
public class BookingsByPersonGui extends JFrame implements ActionListener {
    private RoomBookingController roomBookingController;
    private UniversityRoomBookingModel universityRoomBookingModel;
    private JButton getBookings;
    private JTextField personEmail, personName;
    private JLabel personEmailLabel, personNameLabel;
    private JLabel commandHelp1, commandHelp2;

    /**
     * constructor which sets up the jframe
     *
     * @param roomBookingController      object of controller
     * @param universityRoomBookingModel instance of model
     */
    public BookingsByPersonGui(RoomBookingController roomBookingController, UniversityRoomBookingModel universityRoomBookingModel) {
        super("Bookings done by a Person");
        this.roomBookingController = roomBookingController;
        this.universityRoomBookingModel = universityRoomBookingModel;
        getBookings = new JButton("Get Bookings");
        personEmail = new JTextField(20);
        personName = new JTextField(20);
        personEmailLabel = new JLabel("Person Email");
        personNameLabel = new JLabel("Person Name");
        commandHelp1 = new JLabel("Type these commands in the input box and press subsequent button.\n");
        commandHelp2 = new JLabel("To get all the bookings done by a particular person : <personName> <personEmail>");

        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(commandHelp1);
        add(commandHelp2);
        add(personNameLabel);
        add(personName);
        add(personEmailLabel);
        add(personEmail);
        add(getBookings);

        getBookings.addActionListener(this);

        setSize(600, 200);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * listens for the action event
     *
     * @param e event type
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getBookings) {
            JOptionPane.showMessageDialog(getBookings, roomBookingController.bookingsByAPerson(personName.getText(), personEmail.getText()));
        }
    }
}
