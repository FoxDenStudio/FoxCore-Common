package net.foxdenstudio.foxcore.api.exception;

import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;

/**
 * Factory class for {@link FoxException}s implemented via Guice's AssistedInject.
 * This class contains methods for creating exceptions with arguments
 */
public interface FoxExceptionFactory {

    FoxException newFoxException(String message);

    FoxException newFoxException(String message, Throwable cause);

    FoxException newFoxException(Throwable cause);

    FoxCommandException newFoxCommandException(String message);

    FoxCommandException newFoxCommandException(String message, Throwable cause);

    FoxCommandException newFoxCommandException(Throwable cause);
}
