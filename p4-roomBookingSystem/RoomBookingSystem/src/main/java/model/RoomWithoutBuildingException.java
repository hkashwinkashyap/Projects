package model;

/**
 * custom exception
 */
public class RoomWithoutBuildingException extends Exception {
    public RoomWithoutBuildingException(String message) {
        super(message);
    }
}
