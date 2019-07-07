package net.foxdenstudio.foxcore.api.exception.command;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import net.foxdenstudio.foxcore.api.exception.FoxException;

import javax.inject.Inject;

public class FoxCommandException extends FoxException {

    @Inject
    protected FoxCommandException() {
        super();
    }

    @AssistedInject
    protected FoxCommandException(@Assisted String message) {
        super(message);
    }

    @AssistedInject
    protected FoxCommandException(@Assisted String message, @Assisted Throwable cause) {
        super(message, cause);
    }

    @AssistedInject
    protected FoxCommandException(@Assisted Throwable cause) {
        super(cause);
    }
}
