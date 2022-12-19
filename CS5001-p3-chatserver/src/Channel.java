import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * channel class is instantiated when a new channel is created.
 */
public class Channel {
    /**
     * list of all the users in a given channel.
     */
    private ArrayList<UserConnectionController> usersInChannel = new ArrayList<>();
    private String channelName;
    private BufferedReader get;
    private PrintWriter send;

    /**
     * constructor method which creates a channel and sets name to it.
     *
     * @param channelName string value of name of the channel.
     */
    public Channel(String channelName) {
        this.channelName = channelName;
    }

    /**
     * access method for the list of all users in the channel.
     *
     * @return arraylist of all the users.
     */
    public ArrayList<UserConnectionController> getUsersInChannel() {
        return usersInChannel;
    }

    /**
     * getter method of the channel name.
     *
     * @return string value of the channel name.
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * method which adds the passed user to the channel.
     *
     * @param newUserInChannel user object.
     */
    public void joinChannel(UserConnectionController newUserInChannel) {
        this.usersInChannel.add(newUserInChannel);
    }

    /**
     * removes the given user from the channel.
     *
     * @param deleteUserFromChannel user that should be removed from the channel.
     */
    public void removeFromChannel(UserConnectionController deleteUserFromChannel) {
        this.usersInChannel.remove(deleteUserFromChannel);
    }

    /**
     * method to send the given message to all the users in the channel.
     *
     * @param textMessage string value of the message to be sent.
     */
    public void sendMessage(String textMessage) {
        for (UserConnectionController users : usersInChannel) {
            users.messageUser(textMessage);
        }
    }
}
