import controller.RoomBookingController;
import model.UniversityRoomBookingModel;
import view.clui.RoomBookingClUi;
import view.gui.RoomBookingGui;

/**
 * main class of the system
 */
public class RoomBookingMain extends Thread {
    /**
     * main method of the system which instantiates both the model and controller and passes them to the instances of the two UIs
     *
     * @param args nothing is given in the args
     */
    public static void main(String[] args) {
        UniversityRoomBookingModel universityRoomBookingModel = new UniversityRoomBookingModel();
        RoomBookingController roomBookingController = new RoomBookingController(universityRoomBookingModel);
        RoomBookingGui roomBookingGui = new RoomBookingGui(roomBookingController, universityRoomBookingModel);
        RoomBookingClUi roomBookingClUi = new RoomBookingClUi(roomBookingController);
        Thread gUi = new Thread(roomBookingGui);
        Thread clUi = new Thread(roomBookingClUi);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                gUi.start();
            }
        });
        thread1.start();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                clUi.start();
            }
        });
        thread2.start();
    }
}
