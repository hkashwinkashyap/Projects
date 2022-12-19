package view.gui;

import controller.RoomBookingController;
import model.UniversityRoomBookingModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * gui class for the rooms available in a given time slot
 */
public class RoomsInSlotGui extends JFrame implements ActionListener {
    private RoomBookingController roomBookingController;
    private UniversityRoomBookingModel universityRoomBookingModel;
    private JButton getRooms;
    private JTextField startTime, endTime, date;
    private JLabel startTimeLabel, endTimeLabel, dateLabel;
    private JLabel commandHelp1, commandHelp2;

    /**
     * class' constructor method setting up the jframe
     *
     * @param roomBookingController      instance of controller
     * @param universityRoomBookingModel instance of model
     */
    public RoomsInSlotGui(RoomBookingController roomBookingController, UniversityRoomBookingModel universityRoomBookingModel) {
        super("Rooms in a given time slot");
        this.roomBookingController = roomBookingController;
        this.universityRoomBookingModel = universityRoomBookingModel;
        getRooms = new JButton("Get Rooms");
        startTime = new JTextField(10);
        endTime = new JTextField(10);
        date = new JTextField(15);
        startTimeLabel = new JLabel("Starting Time");
        endTimeLabel = new JLabel("Ending TIme");
        dateLabel = new JLabel("Date");
        commandHelp1 = new JLabel("Type these commands in the input box and press subsequent button.\n");
        commandHelp2 = new JLabel("To get all the rooms free for the whole of given time slot : <startTime> <endTime> <date in dd—mm—yyyy>");

        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(commandHelp1);
        add(commandHelp2);
        add(startTimeLabel);
        add(startTime);
        add(endTimeLabel);
        add(endTime);
        add(dateLabel);
        add(date);
        add(getRooms);

        getRooms.addActionListener(this);

        setSize(750, 200);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * action listener method
     *
     * @param e event type
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getRooms) {
            JOptionPane.showMessageDialog(getRooms, roomBookingController.roomsAvailableInGivenTimeSlot(startTime.getText(), endTime.getText(), date.getText()));
        }
    }
}
