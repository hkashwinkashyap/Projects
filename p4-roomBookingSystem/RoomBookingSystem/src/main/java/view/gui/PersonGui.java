package view.gui;

import controller.RoomBookingController;
import model.UniversityRoomBookingModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * gui class for adding and removing person
 */
public class PersonGui extends JFrame implements ActionListener {
    private RoomBookingController roomBookingController;
    private UniversityRoomBookingModel universityRoomBookingModel;
    private JButton addPerson, removePerson;
    private JTextField personName, personEmail;
    private JLabel personNameLabel, personEmailLabel;
    private JLabel commandHelp1, commandHelp2, commandHelp3;

    /**
     * constructor method which also sets the jframe as required
     *
     * @param roomBookingController      instance of controller
     * @param universityRoomBookingModel object of model
     */
    public PersonGui(RoomBookingController roomBookingController, UniversityRoomBookingModel universityRoomBookingModel) {
        super("Add or Remove a Person");
        this.roomBookingController = roomBookingController;
        this.universityRoomBookingModel = universityRoomBookingModel;
        addPerson = new JButton("Add Person");
        removePerson = new JButton("Remove Person");
        personName = new JTextField(20);
        personEmail = new JTextField(30);
        personNameLabel = new JLabel("Person name");
        personEmailLabel = new JLabel("Person email address");
        commandHelp1 = new JLabel("Type these commands in the input box and press subsequent button.\n");
        commandHelp2 = new JLabel("To add a person : <personName> <emailAddress>\n");
        commandHelp3 = new JLabel("To remove a person : <emailAddressOfThePerson>");
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(commandHelp1);
        add(commandHelp2);
        add(commandHelp3);
        add(personNameLabel);
        add(personName);
        add(personEmailLabel);
        add(personEmail);
        add(addPerson);
        add(removePerson);

        addPerson.addActionListener(this);
        removePerson.addActionListener(this);

        setSize(720, 200);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * listener method
     *
     * @param e event type
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPerson) {
            try {
                JOptionPane.showMessageDialog(addPerson, roomBookingController.addPerson(personName.getText(), personEmail.getText()));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(addPerson, ex);
            }
        } else if (e.getSource() == removePerson) {
            JOptionPane.showMessageDialog(removePerson, roomBookingController.removePerson(personEmail.getText()));
        }
    }
}
