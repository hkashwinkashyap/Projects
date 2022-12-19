package view.gui;

import controller.RoomBookingController;
import model.UniversityRoomBookingModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * gui class for a room
 */
public class RoomGui extends JFrame implements ActionListener {
    private RoomBookingController roomBookingController;
    private UniversityRoomBookingModel universityRoomBookingModel;
    private JButton addRoom, removeRoom;
    private JTextField roomName, buildingName;
    private JLabel roomNameLabel, buildingNameLabel;
    private JLabel commandHelp1, commandHelp2, commandHelp3;

    /**
     * constructor of the class that sets the jframe
     *
     * @param roomBookingController      instance of controller
     * @param universityRoomBookingModel instance of model
     */
    public RoomGui(RoomBookingController roomBookingController, UniversityRoomBookingModel universityRoomBookingModel) {
        super("Add or Remove a Room");
        this.roomBookingController = roomBookingController;
        this.universityRoomBookingModel = universityRoomBookingModel;
        addRoom = new JButton("Add Room");
        removeRoom = new JButton("Remove Room");
        roomName = new JTextField(20);
        buildingName = new JTextField(30);
        roomNameLabel = new JLabel("Room name");
        buildingNameLabel = new JLabel("Building name");
        commandHelp1 = new JLabel("Type these commands in the input box and press subsequent button.\n");
        commandHelp2 = new JLabel("To add a room : <roomName> <buildingName>\n");
        commandHelp3 = new JLabel("To remove a room : <roomName> <buildingName>");

        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(commandHelp1);
        add(commandHelp2);
        add(commandHelp3);
        add(roomNameLabel);
        add(roomName);
        add(buildingNameLabel);
        add(buildingName);
        add(addRoom);
        add(removeRoom);

        addRoom.addActionListener(this);
        removeRoom.addActionListener(this);

        setSize(650, 200);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * action listener
     *
     * @param e event which has the information of the source
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addRoom) {
            try {
                JOptionPane.showMessageDialog(addRoom, roomBookingController.addRoom(roomName.getText(), buildingName.getText()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addRoom, ex);
            }
        } else if (e.getSource() == removeRoom) {
            try {
                JOptionPane.showMessageDialog(removeRoom, roomBookingController.removeRoom(roomName.getText(), buildingName.getText()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(removeRoom, ex);
            }
        }
    }
}
