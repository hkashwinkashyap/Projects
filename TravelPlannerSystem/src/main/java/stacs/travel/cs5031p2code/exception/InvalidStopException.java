package stacs.travel.cs5031p2code.exception;

/**
 * The Exception class for when invalid stop data is called within methods.
 *
 * @version 0.0.1
 */
public class InvalidStopException extends Exception {
    /**
     * The constructor method.
     *
     * @param message the message needed to tip.
     */
    public InvalidStopException(String message) {
        super(message);
    }
}
