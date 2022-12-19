package view.gui;

import controller.RoomBookingController;
import model.UniversityRoomBookingModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * gui class for the schedule of a room
 */
public class ScheduleOfRoomGui extends JFrame implements ActionListener {
    private RoomBookingController roomBookingController;
    private UniversityRoomBookingModel universityRoomBookingModel;
    private JButton getSchedule;
    private JTextField roomName, date;
    private JLabel roomNameLabel, dateLabel;
    private JLabel commandHelp1, commandHelp2;

    /**
     * constructor method also setting the jframe
     *
     * @param roomBookingController      object of controller
     * @param universityRoomBookingModel instance of model of the system
     */
    public ScheduleOfRoomGui(RoomBookingController roomBookingController, UniversityRoomBookingModel universityRoomBookingModel) {
        super("Schedule of a Room");
        this.roomBookingController = roomBookingController;
        this.universityRoomBookingModel = universityRoomBookingModel;
        getSchedule = new JButton("Get Schedule");
        roomName = new JTextField(20);
        date = new JTextField(15);
        roomNameLabel = new JLabel("Room Name");
        dateLabel = new JLabel("Date");
        commandHelp1 = new JLabel("Type these commands in the input box and press subsequent button.\n");
        commandHelp2 = new JLabel("To get the schedule for a particular room : <roomName> <date in dd—mm—yyyy>");

        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(commandHelp1);
        add(commandHelp2);
        add(roomNameLabel);
        add(roomName);
        add(dateLabel);
        add(date);
        add(getSchedule);

        getSchedule.addActionListener(this);

        setSize(600, 200);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * action listener
     *
     * @param e event type and also has the source of the event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == getSchedule) {
            JTextArea textArea = new JTextArea(30, 30);
            textArea.setText(roomBookingController.scheduleOfARoom(roomName.getText(), date.getText()));
            textArea.setEditable(false);

            JScrollPane scrollPane = new JScrollPane(textArea);

            JOptionPane.showMessageDialog(getSchedule, scrollPane);
        }
    }
}
