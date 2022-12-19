package model;

/**
 * custom exception
 */
public class NameAlreadyExistsException extends Exception {
    public NameAlreadyExistsException(String message) {
        super(message);
    }
}
