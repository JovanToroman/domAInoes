package util;

public class ValidationException extends Throwable {
    public ValidationException() {
        super("Invalid input");
    }
}
