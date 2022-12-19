import static java.lang.Integer.parseInt;

/**
 * IrcServerMain is the class which implements the main function of the chat server.
 */
public class IrcServerMain {
    /**
     * main function instantiates and runs the server.
     *
     * @param args the server name and port number are passed as arguments.
     * @throws Exception as we are using the parseint method, that method may throw an exception.
     */
    public static void main(String[] args) throws Exception {
        try {
            if (args.length == 2) {
                IrcServer ircServer = new IrcServer();
                IrcServer.setServerName(args[0]);
                IrcServer.setServerPortNumber(parseInt(args[1]));
                /**
                 * creating a thread for the server.
                 */
                Thread thread = new Thread(ircServer);
                thread.run();
            } else {
                System.out.println("Usage: java IrcServerMain <server_name> <port>");
            }
        } catch (NumberFormatException e) {
            System.out.println("Usage: java IrcServerMain <server_name> <port>");
        }
    }


}
