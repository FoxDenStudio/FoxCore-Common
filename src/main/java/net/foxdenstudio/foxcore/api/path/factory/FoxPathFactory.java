package net.foxdenstudio.foxcore.api.path.factory;

import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.path.FoxPath;

import javax.annotation.Nonnull;

public interface FoxPathFactory<T extends FoxPath> {

    @Nonnull
    T getPath(@Nonnull String input) throws FoxCommandException;

    // TODO Implement some kind of mechanism for tab completion.
}
