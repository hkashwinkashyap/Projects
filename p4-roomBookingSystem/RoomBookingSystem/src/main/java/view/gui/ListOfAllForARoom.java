package view.gui;

import controller.RoomBookingController;
import model.UniversityRoomBookingModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * gui class for list of all the transactions for a room
 */
public class ListOfAllForARoom extends JFrame implements ActionListener {
    private RoomBookingController roomBookingController;
    private UniversityRoomBookingModel universityRoomBookingModel;
    private JButton getList;
    private JTextField roomName;
    private JLabel roomNameLabel;
    private JLabel commandHelp1, commandHelp2;

    /**
     * constructor method which lays out the jframe
     *
     * @param roomBookingController      object of controller
     * @param universityRoomBookingModel instance of model of the system
     */
    public ListOfAllForARoom(RoomBookingController roomBookingController, UniversityRoomBookingModel universityRoomBookingModel) {
        super("List of all transactions for a Room");
        this.roomBookingController = roomBookingController;
        this.universityRoomBookingModel = universityRoomBookingModel;
        getList = new JButton("Get List");
        roomName = new JTextField(20);
        roomNameLabel = new JLabel("Room Name");
        commandHelp1 = new JLabel("Type these commands in the input box and press subsequent button.\n");
        commandHelp2 = new JLabel("To get the list of all transactions made against a particular room : <roomName>");

        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(commandHelp1);
        add(commandHelp2);
        add(roomNameLabel);
        add(roomName);
        add(getList);

        getList.addActionListener(this);

        setSize(650, 200);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * action listener
     *
     * @param e action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getList) {
            JOptionPane.showMessageDialog(getList, roomBookingController.listOfAllBookingForARoom(roomName.getText()));
        }
    }
}
