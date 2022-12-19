package view.gui;

import controller.RoomBookingController;
import model.UniversityRoomBookingModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * gui class for adding and removing building
 */
public class BuildingGui extends JFrame implements ActionListener {
    private RoomBookingController roomBookingController;
    private UniversityRoomBookingModel universityRoomBookingModel;
    private JButton addBuilding, removeBuilding;
    private JTextField buildingName, buildingAddress;
    private JLabel buildingNameLabel, buildingAddressLabel;
    private JLabel commandHelp1, commandHelp2, commandHelp3;

    /**
     * constructor method which sets the jframe
     *
     * @param roomBookingController      instance of the controller of the system
     * @param universityRoomBookingModel instance of the model
     */
    public BuildingGui(RoomBookingController roomBookingController, UniversityRoomBookingModel universityRoomBookingModel) {
        super("Add or Remove a Building");
        this.roomBookingController = roomBookingController;
        this.universityRoomBookingModel = universityRoomBookingModel;
        addBuilding = new JButton("Add Building");
        removeBuilding = new JButton("Remove Building");
        buildingName = new JTextField(20);
        buildingAddress = new JTextField(30);
        buildingNameLabel = new JLabel("Building name");
        buildingAddressLabel = new JLabel("Building address");
        commandHelp1 = new JLabel("Type these commands in the input box and press subsequent button.\n");
        commandHelp2 = new JLabel("To add a building : <buildingName> <buildingAddress>\n");
        commandHelp3 = new JLabel("To remove a building : <buildingName>");
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(commandHelp1);
        add(commandHelp2);
        add(commandHelp3);
        add(buildingNameLabel);
        add(buildingName);
        add(buildingAddressLabel);
        add(buildingAddress);
        add(addBuilding);
        add(removeBuilding);

        addBuilding.addActionListener(this);
        removeBuilding.addActionListener(this);

        setSize(680, 200);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * action listener
     *
     * @param e event type
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBuilding) {
            try {
                JOptionPane.showMessageDialog(addBuilding, roomBookingController.addBuilding(buildingName.getText(), buildingAddress.getText()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addBuilding, ex);
            }
        } else if (e.getSource() == removeBuilding) {
            JOptionPane.showMessageDialog(removeBuilding, roomBookingController.removeBuilding(buildingName.getText()));
        }
    }
}
