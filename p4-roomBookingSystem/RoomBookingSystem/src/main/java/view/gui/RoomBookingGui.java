package view.gui;

import controller.RoomBookingController;
import model.UniversityRoomBookingModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * class for the gui of the system
 */
public class RoomBookingGui extends JFrame implements ActionListener, Runnable {
    private RoomBookingController roomBookingController;
    private UniversityRoomBookingModel universityRoomBookingModel;
    private JButton addOrRemovePersonButton;
    private JButton addOrRemoveBuildingButton;
    private JButton addOrRemoveRoomButton;
    private JButton bookOrCancelARoomButton;
    private JButton roomsInGivenSlotButton;
    private JButton roomsAtGivenTimeButton;
    private JButton scheduleOfARoomButton;
    private JButton bookingsByAPersonButton;
    private JButton listOfAllBookingsButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton helpButton;
    private JPanel homeList;
    private JLabel selectAnyOneLabel;

    /**
     * constructor method of the class
     *
     * @param roomBookingController      instance of controller of the system
     * @param universityRoomBookingModel instance of model of the system
     */
    public RoomBookingGui(RoomBookingController roomBookingController, UniversityRoomBookingModel universityRoomBookingModel) {
        super("University Room Booking System");
        this.roomBookingController = roomBookingController;
        this.universityRoomBookingModel = universityRoomBookingModel;
    }

    /**
     * listens for the event and responds appropriately by calling relevant jframe
     *
     * @param e type of the event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addOrRemovePersonButton) {
            new PersonGui(roomBookingController, universityRoomBookingModel);
        } else if (e.getSource() == helpButton) {
            JOptionPane.showMessageDialog(helpButton, roomBookingController.help());
        } else if (e.getSource() == addOrRemoveBuildingButton) {
            new BuildingGui(roomBookingController, universityRoomBookingModel);
        } else if (e.getSource() == addOrRemoveRoomButton) {
            new RoomGui(roomBookingController, universityRoomBookingModel);
        } else if (e.getSource() == bookOrCancelARoomButton) {
            new BookingGui(roomBookingController, universityRoomBookingModel);
        } else if (e.getSource() == saveButton) {
            try {
                JOptionPane.showMessageDialog(saveButton, roomBookingController.saveAll());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(saveButton, ex);
            }
        } else if (e.getSource() == loadButton) {
            try {
                JOptionPane.showMessageDialog(loadButton, roomBookingController.loadAll());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(loadButton, ex);
            }
        } else if (e.getSource() == roomsAtGivenTimeButton) {
            new RoomsAtGivenGui(roomBookingController, universityRoomBookingModel);
        } else if (e.getSource() == roomsInGivenSlotButton) {
            new RoomsInSlotGui(roomBookingController, universityRoomBookingModel);
        } else if (e.getSource() == scheduleOfARoomButton) {
            new ScheduleOfRoomGui(roomBookingController, universityRoomBookingModel);
        } else if (e.getSource() == bookingsByAPersonButton) {
            new BookingsByPersonGui(roomBookingController, universityRoomBookingModel);
        } else if (e.getSource() == listOfAllBookingsButton) {
            new ListOfAllForARoom(roomBookingController, universityRoomBookingModel);
        }
    }

    /**
     * run method which is run when a thread is started which adds all the buttons and layout on the jframe
     */
    @Override
    public void run() {
        addOrRemovePersonButton = new JButton("Add/Remove Person");
        addOrRemoveBuildingButton = new JButton("Add/Remove Building");
        addOrRemoveRoomButton = new JButton("Add/Remove Room");
        bookOrCancelARoomButton = new JButton("Book/Cancel a room");
        roomsInGivenSlotButton = new JButton("Rooms in given slot");
        roomsAtGivenTimeButton = new JButton("Rooms at given time for 5 minutes or more");
        scheduleOfARoomButton = new JButton("Schedule of a room");
        bookingsByAPersonButton = new JButton("Bookings by a Person");
        listOfAllBookingsButton = new JButton("List of transaction for a Room");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        helpButton = new JButton("View list of commands");
        homeList = new JPanel();
        selectAnyOneLabel = new JLabel("Select any one");

        setLayout(new FlowLayout(FlowLayout.CENTER, 800, 15));
        add(selectAnyOneLabel);
        add(helpButton);
        add(addOrRemovePersonButton);
        add(addOrRemoveBuildingButton);
        add(addOrRemoveRoomButton);
        add(bookOrCancelARoomButton);
        add(roomsInGivenSlotButton);
        add(roomsAtGivenTimeButton);
        add(scheduleOfARoomButton);
        add(bookingsByAPersonButton);
        add(listOfAllBookingsButton);
        add(saveButton);
        add(loadButton);

        addOrRemovePersonButton.addActionListener(this);
        addOrRemoveBuildingButton.addActionListener(this);
        addOrRemoveRoomButton.addActionListener(this);
        bookOrCancelARoomButton.addActionListener(this);
        roomsInGivenSlotButton.addActionListener(this);
        roomsAtGivenTimeButton.addActionListener(this);
        scheduleOfARoomButton.addActionListener(this);
        bookingsByAPersonButton.addActionListener(this);
        listOfAllBookingsButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        helpButton.addActionListener(this);

        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
