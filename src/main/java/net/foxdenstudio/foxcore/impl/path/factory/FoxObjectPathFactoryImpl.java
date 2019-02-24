package net.foxdenstudio.foxcore.impl.path.factory;

import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.path.components.FoxObjectPath;
import net.foxdenstudio.foxcore.api.path.factory.FoxObjectPathFactory;
import net.foxdenstudio.foxcore.impl.path.FoxHierarchicalPathImpl;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Optional;

@Singleton
public class FoxObjectPathFactoryImpl extends FoxPathFactoryBaseImpl implements FoxObjectPathFactory {

    @Inject
    private FoxObjectPathFactoryImpl() {
    }

    @SuppressWarnings("Duplicates")
    @Override
    public FoxObjectPath getPath(@Nonnull String input) throws FoxCommandException {
        if (input.isEmpty())
            throw this.exceptionFactory.newFoxCommandException("Object path may not be empty!");

        String[] parts = input.split("/+");

        if (parts.length == 0)
            throw this.exceptionFactory.newFoxCommandException("Object path may not be zero length!");

        if (parts[0].isEmpty()) {
            parts = Arrays.copyOfRange(parts, 1, parts.length);
        }

        if (parts.length == 0)
            throw this.exceptionFactory.newFoxCommandException("Object path may not be zero length!");

        for (String part : parts) {
            if (!nameChecker.isClean(part)) {
                throw exceptionFactory.newFoxCommandException("Name \"" + part + "\" is invalid!");
            }
        }

        return FoxHierarchicalPathImpl.of(parts);
    }
}
