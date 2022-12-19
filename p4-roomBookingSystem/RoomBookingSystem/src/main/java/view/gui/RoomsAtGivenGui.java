package view.gui;

import controller.RoomBookingController;
import model.UniversityRoomBookingModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * gui class for rooms at a given time
 */
public class RoomsAtGivenGui extends JFrame implements ActionListener {
    private RoomBookingController roomBookingController;
    private UniversityRoomBookingModel universityRoomBookingModel;
    private JButton getRooms;
    private JTextField time, date;
    private JLabel timeLabel, dateLabel;
    private JLabel commandHelp1, commandHelp2;

    /**
     * class' constructor method that also sets the jframe
     *
     * @param roomBookingController      object of controller
     * @param universityRoomBookingModel object of model
     */
    public RoomsAtGivenGui(RoomBookingController roomBookingController, UniversityRoomBookingModel universityRoomBookingModel) {
        super("Rooms available at a given time for 5 minutes or more");
        this.roomBookingController = roomBookingController;
        this.universityRoomBookingModel = universityRoomBookingModel;
        getRooms = new JButton("Get Rooms");
        time = new JTextField(20);
        date = new JTextField(15);
        timeLabel = new JLabel("Time");
        dateLabel = new JLabel("Date");
        commandHelp1 = new JLabel("Type these commands in the input box and press subsequent button.\n");
        commandHelp2 = new JLabel("To get all the rooms free at a given time : <timeRequired> <date in dd—mm—yyyy>");

        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(commandHelp1);
        add(commandHelp2);
        add(timeLabel);
        add(time);
        add(dateLabel);
        add(date);
        add(getRooms);

        getRooms.addActionListener(this);

        setSize(550, 200);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * action listener which listens to the event
     *
     * @param e event type
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getRooms) {
            JOptionPane.showMessageDialog(getRooms, roomBookingController.chooseTimeAndSeeAllRoomsAvailable(time.getText(), date.getText()));
        }
    }
}
