import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * IrcServer class is the server class which is to be instantiated in the main method.
 */
public class IrcServer implements Runnable {
    /**
     * the port number where the server is run. this is given by the arguments in main method.
     */
    private static int serverPortNumber;
    /**
     * server name is also given by the main method's arguments.
     */
    private static String serverName;
    /**
     * holds the list of all channels present in the server.
     */
    private static ArrayList<Channel> channels;
    /**
     * list of all the connected clients to the server.
     */
    private static ArrayList<UserConnectionController> connectedUsers;
    private ServerSocket serverSocket;

    /**
     * access method for the channels list.
     *
     * @return array list of channels present.
     */
    public static ArrayList<Channel> getChannels() {
        return channels;
    }

    /**
     * setter for the port number of the server.
     *
     * @param serverPortNumber int value of the port number.
     */
    public static void setServerPortNumber(int serverPortNumber) {
        IrcServer.serverPortNumber = serverPortNumber;
    }

    /**
     * getter method of the server name.
     *
     * @return string value of the server name.
     */
    public static String getServerName() {
        return serverName;
    }

    /**
     * setter for the server name.
     *
     * @param serverName string value of the server name.
     */
    public static void setServerName(String serverName) {
        IrcServer.serverName = serverName;
    }

    /**
     * access method for the list of the connected users.
     *
     * @return array list of connected users.
     */
    public static ArrayList<UserConnectionController> getConnectedUsers() {
        return connectedUsers;
    }

    /**
     * run method which is called when a thread is created.
     */
    @Override
    public void run() {
        try {
            connectedUsers = new ArrayList<>();
            channels = new ArrayList<>();
            serverSocket = new ServerSocket(serverPortNumber);
            /**
             * created a thread pool so that the threads are use is optimised.
             */
            ExecutorService threadPool = Executors.newCachedThreadPool();
            while (true) {
                Socket newConnection = serverSocket.accept();
                UserConnectionController newUser = new UserConnectionController(newConnection);
                /**
                 * creating and assigning a thread to each connected user.
                 */
                threadPool.execute(newUser);
            }
            /**
             * this might be thrown by the socket.
             */
        } catch (IOException e) {
            e.printStackTrace();
            /**
             * handled the exception that the array lists above might through.
             */
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
