package net.foxdenstudio.foxsuite.foxcore.api.exception.command;

import net.foxdenstudio.foxsuite.foxcore.api.exception.FoxException;

public class FoxCommandException extends FoxException {

    public FoxCommandException() {
    }

    public FoxCommandException(String message) {
        super(message);
    }

    public FoxCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public FoxCommandException(Throwable cause) {
        super(cause);
    }
}
