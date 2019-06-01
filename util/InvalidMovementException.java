package util;

public class InvalidMovementException extends Throwable {
    public InvalidMovementException() {
        super("Invalid move");
    }
}
