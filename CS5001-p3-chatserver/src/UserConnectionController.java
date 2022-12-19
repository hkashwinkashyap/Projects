import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * user connection controller controls and handles the connection of a given user.
 */
public class UserConnectionController implements Runnable {
    private boolean isConnected;
    private Socket user;
    private BufferedReader get;
    private PrintWriter send;
    private String nickname;
    private String realName;
    private String userName;
    private boolean registered = false;
    private String serverNameWithColon = ":" + IrcServer.getServerName();

    /**
     * constructor method which instantiates the user.
     *
     * @param user assigns a socket to a user.
     */
    public UserConnectionController(Socket user) {
        this.user = user;
        this.isConnected = true;
    }

    /**
     * setter of user name.
     *
     * @param userName string value of username.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * access method of registered.
     *
     * @return boolean value if the user is registered or not.
     */
    public boolean isRegistered() {
        return registered;
    }

    /**
     * setter for registered.
     *
     * @param registered boolean value.
     */
    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    /**
     * getter method for nickname of the user.
     *
     * @return string value of the user's nickname.
     */
    public String getNickname() {
        String dummyName = "*";
        return nickname != null ? nickname : dummyName;
    }

    /**
     * setter for nickname.
     *
     * @param nickname string of nickname.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * setter for real name of the user.
     *
     * @param realName string of real name.
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * method which sends message to the user.
     *
     * @param textMessage string of the message to be sent.
     */
    public void messageUser(String textMessage) {
        send.println(textMessage);
    }

    /**
     * handles the quit command.
     */
    private void quitController() {
        if (IrcServer.getConnectedUsers() != null) {
            if (this.isRegistered()) {
                for (UserConnectionController user : IrcServer.getConnectedUsers()) {
                    user.messageUser(":" + this.getNickname() + " QUIT");
                }
                for (Channel allChannels : IrcServer.getChannels()) {
                    if (allChannels.getUsersInChannel().contains(this)) {
                        allChannels.getUsersInChannel().remove(this);
                    }
                }
                IrcServer.getConnectedUsers().remove(this);
            }
        }
    }

    /**
     * sets nickname for the user.
     *
     * @param uncheckedNickname inout string value of the nickname which is to be checked and assigned.
     */
    private void nickNameController(String uncheckedNickname) {
        Pattern req = Pattern.compile("[^a-z0-9_]", Pattern.CASE_INSENSITIVE);
        Matcher notReq = req.matcher(uncheckedNickname);
        String sampleNickName = "a23456789";
        if (notReq.find() || Character.isDigit(uncheckedNickname.charAt(0)) || uncheckedNickname.length() > sampleNickName.length() || uncheckedNickname.length() == 0) {
            messageUser(serverNameWithColon + " 400 " + this.getNickname() + " :Invalid nickname");
        } else {
            boolean isTaken = false;
            for (UserConnectionController user : IrcServer.getConnectedUsers()) {
                if (user.getNickname().equals(uncheckedNickname)) {
                    messageUser(serverNameWithColon + " 400 " + this.getNickname() + " :Nickname is already taken");
                }
            }
            if (!isTaken) {
                this.setNickname(uncheckedNickname);
                IrcServer.getConnectedUsers().add(this);
            }
        }
    }

    /**
     * controls username check and registering a user.
     *
     * @param givenUserName input string of the username.
     */
    private void userController(String givenUserName) {
        String uncheckedUserName = givenUserName;
        if (uncheckedUserName.contains(" ") || this.getNickname() == null) {
            messageUser(serverNameWithColon + " 400 " + this.getNickname() + " :Invalid arguments to USER command");
        } else if (this.getNickname() != null) {
            if (this.isRegistered()) {
                messageUser(serverNameWithColon + " 400 " + this.getNickname() + " :You are already registered");
            } else {
                boolean isTaken = false;
                for (UserConnectionController user : IrcServer.getConnectedUsers()) {
                    if (user.getNickname().equals(givenUserName)) {
                        messageUser(serverNameWithColon + " 400 " + this.getNickname() + " :User Name is already taken");
                    }
                }
                if (!isTaken) {
                    this.setUserName(uncheckedUserName);
                    this.setRegistered(true);
                    messageUser(serverNameWithColon + " 001 " + this.getNickname() + " :Welcome to the IRC network, " + this.nickname);
                }
            }
        }
    }

    /**
     * controls the join command.
     *
     * @param uncheckedChannelName input string of the channel name.
     */
    private void joinController(String uncheckedChannelName) {
        if (!this.isRegistered()) {
            messageUser(serverNameWithColon + " 400 " + this.getNickname() + " :You need to register first");
        } else if (uncheckedChannelName.charAt(0) != '#') {
            messageUser(serverNameWithColon + " 400 " + this.getNickname() + " :Invalid channel name");
        } else {
            boolean channelExits = false;
            for (Channel channel : IrcServer.getChannels()) {
                if (channel.getChannelName().equals(uncheckedChannelName)) {
                    channelExits = true;
                }
            }
            if (channelExits) {
                for (Channel channel : IrcServer.getChannels()) {
                    if (channel.getChannelName().equals(uncheckedChannelName)) {
                        channel.joinChannel(this);
                        channel.sendMessage(":" + this.getNickname() + " JOIN " + uncheckedChannelName);
                    }
                }
            } else {
                Channel newChannel = new Channel(uncheckedChannelName);
                IrcServer.getChannels().add(newChannel);
                newChannel.joinChannel(this);
                newChannel.sendMessage(":" + this.getNickname() + " JOIN " + uncheckedChannelName);
            }
        }
    }

    /**
     * controller for part command.
     *
     * @param uncheckedChannelName user input string of the channel name.
     */
    private void partController(String uncheckedChannelName) {
        if (!this.isRegistered()) {
            messageUser(serverNameWithColon + " 400 " + this.getNickname() + " :You need to register first");
        } else {
            boolean channelExits = false;
            for (Channel channel : IrcServer.getChannels()) {
                if (channel.getChannelName().equals(uncheckedChannelName)) {
                    channelExits = true;
                }
            }
            if (channelExits) {
                for (Channel channel : IrcServer.getChannels()) {
                    if (channel.getChannelName().equals(uncheckedChannelName)) {
                        channel.sendMessage(":" + this.getNickname() + " PART " + uncheckedChannelName);
                        channel.removeFromChannel(this);
                    }
                }
            } else {
                messageUser(serverNameWithColon + " 400 " + this.getNickname() + " :No channel exists with that name");
            }
        }
    }

    /**
     * private message command controller.
     *
     * @param uncheckedPrivmsg string array of user input.
     */
    private void privmsgController(String[] uncheckedPrivmsg) {
        String[] requiredInput = {"PRIVMSG", "<target>", ":<message>"};
        if (uncheckedPrivmsg.length < requiredInput.length) {
            messageUser(serverNameWithColon + " 400 " + this.getNickname() + " :Invalid arguments to PRIVMSG command");
        } else {
            if (!this.isRegistered()) {
                messageUser(serverNameWithColon + " 400 " + this.getNickname() + " :You need to register first");
            } else {
                String target = uncheckedPrivmsg[1];
                String message = new String();
                for (int i = 2; i < uncheckedPrivmsg.length; i++) {
                    message += " " + uncheckedPrivmsg[i];
                }
                boolean userMatch = false;
                for (UserConnectionController user : IrcServer.getConnectedUsers()) {
                    if (user.getNickname().equals(target)) {
                        userMatch = true;
                    }
                }
                boolean channelMatch = false;
                for (Channel channel : IrcServer.getChannels()) {
                    if (channel.getChannelName().equals(target)) {
                        channelMatch = true;
                    }
                }
                if (userMatch) {
                    for (UserConnectionController user : IrcServer.getConnectedUsers()) {
                        if (user.getNickname().equals(target)) {
                            user.messageUser(":" + this.getNickname() + " PRIVMSG " + target + message);
                        }
                    }
                } else if (channelMatch) {
                    for (Channel channel : IrcServer.getChannels()) {
                        if (channel.getChannelName().equals(target)) {
                            channel.sendMessage(":" + this.getNickname() + " PRIVMSG " + target + message);
                        }
                    }
                } else {
                    messageUser(serverNameWithColon + " 400 " + this.getNickname() + " :No channel or user exists with that name");
                }
            }
        }
    }

    /**
     * controls names command.
     *
     * @param uncheckedChannelName user input string of channel name.
     */
    private void namesController(String uncheckedChannelName) {
        if (!this.isRegistered()) {
            messageUser(serverNameWithColon + " 400 " + this.getNickname() + " :You need to register first");
        } else {
            boolean channelMatch = IrcServer.getChannels().stream().anyMatch(user -> user.getChannelName().equals(uncheckedChannelName));
            if (!channelMatch) {
                messageUser(serverNameWithColon + " 400 " + this.getNickname() + " :No channel exits with that name");
            } else {
                ArrayList<Channel> channelList = (ArrayList<Channel>) IrcServer.getChannels().stream().filter(user -> user.getChannelName().equals(uncheckedChannelName)).collect(Collectors.toList());
                Channel channel = channelList.get(0);
                String nameList = new String();
                for (UserConnectionController user : channel.getUsersInChannel()) {
                    nameList += user.getNickname() + " ";
                }
                messageUser(serverNameWithColon + " 353 " + this.getNickname() + " = " + uncheckedChannelName + " :" + nameList);
            }
        }
    }

    /**
     * handle list command.
     */
    private void listController() {
        if (!this.isRegistered()) {
            messageUser(serverNameWithColon + " 400 " + this.getNickname() + " :You need to register first");
        } else {
            for (Channel channel : IrcServer.getChannels()) {
                messageUser(serverNameWithColon + " 322 " + this.getNickname() + " " + channel.getChannelName());
            }
            messageUser(serverNameWithColon + " 323 " + this.getNickname() + " :End of LIST");
        }
    }

    /**
     * gives present time of the server to the user.
     */
    private void timeController() {
        String time = String.valueOf(LocalDateTime.now());
        messageUser(serverNameWithColon + " 391 * :2022-1");
    }

    /**
     * gives server's info to the user.
     */
    private void infoController() {
        messageUser(serverNameWithColon + " 371 * :This is an IRC server written in JAVA by the Terminator in 1995.");
    }

    /**
     * handles ping command.
     *
     * @param testMessage string value just to check if the user is connected to the server.
     */
    private void pingController(String testMessage) {
        messageUser("PONG " + testMessage);
    }

    /**
     * this method is executed by a thread.
     */
    @Override
    public void run() {
        try {
            get = new BufferedReader(new InputStreamReader(user.getInputStream()));
            send = new PrintWriter(user.getOutputStream(), true);
            while (isConnected) {
                String userInput = get.readLine();
                if (!userInput.equals("QUIT")) {
                    String[] splittingTheGivenInput = userInput.split(" ");
                    if (userInput.startsWith("NICK ")) {
                        nickNameController(splittingTheGivenInput[1]);
                        continue;
                    }
                    if (userInput.startsWith("USER ")) {
                        String[] requiredInput = {"USER", "<username>", "0", "*", ":<real_name>"};
                        if (splittingTheGivenInput.length < requiredInput.length) {
                            messageUser(serverNameWithColon + " 400 * :Not enough arguments");
                            continue;
                        }
                        String givenRealName = new String();
                        for (int i = requiredInput.length - 1; i < splittingTheGivenInput.length; i++) {
                            givenRealName += splittingTheGivenInput[i] + " ";
                        }
                        this.setRealName(givenRealName);
                        userController(splittingTheGivenInput[1]);
                        continue;
                    }
                    if (userInput.startsWith("JOIN ")) {
                        joinController(splittingTheGivenInput[1]);
                        continue;
                    }
                    if (userInput.startsWith("PART ")) {
                        partController(splittingTheGivenInput[1]);
                        continue;
                    }
                    if (userInput.startsWith("PRIVMSG ")) {
                        privmsgController(splittingTheGivenInput);
                        continue;
                    }
                    if (userInput.startsWith("NAMES ")) {
                        namesController(splittingTheGivenInput[1]);
                        continue;
                    }
                    if (userInput.startsWith("LIST")) {
                        listController();
                        continue;
                    }
                    if (userInput.startsWith("TIME")) {
                        timeController();
                        continue;
                    }
                    if (userInput.startsWith("INFO")) {
                        infoController();
                        continue;
                    }
                    if (userInput.startsWith("PING ")) {
                        pingController(splittingTheGivenInput[1]);
                        continue;
                    }
                } else {
                    quitController();
                    send.close();
                    get.close();
                    user.close();
                    break;
                }
            }
            /**
             * handling socket exception.
             */
        } catch (Exception e) {
            e.printStackTrace();
            try {
                send.close();
                get.close();
                user.close();
                /**
                 * handling the exception socket close might throw.
                 */
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
