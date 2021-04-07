package net.foxdenstudio.foxsuite.foxcore.api.exception;

public class FoxException extends Exception {

    public FoxException() {
        super();
    }

    public FoxException(String message) {
        super(message);
    }

    public FoxException(String message, Throwable cause) {
        super(message, cause);
    }

    public FoxException(Throwable cause) {
        super(cause);
    }
}
