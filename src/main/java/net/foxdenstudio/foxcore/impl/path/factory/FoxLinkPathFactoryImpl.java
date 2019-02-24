package net.foxdenstudio.foxcore.impl.path.factory;

import net.foxdenstudio.foxcore.api.exception.FoxException;
import net.foxdenstudio.foxcore.api.exception.FoxExceptionFactory;
import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.path.FoxHierarchicalPath;
import net.foxdenstudio.foxcore.api.path.components.FoxLinkPath;
import net.foxdenstudio.foxcore.api.path.factory.FoxLinkPathFactory;
import net.foxdenstudio.foxcore.api.util.NameChecker;
import net.foxdenstudio.foxcore.impl.path.FoxHierarchicalPathImpl;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Optional;

@Singleton
public class FoxLinkPathFactoryImpl extends FoxPathFactoryBaseImpl implements FoxLinkPathFactory {

    @Inject
    private FoxLinkPathFactoryImpl(){}

    @SuppressWarnings("Duplicates")
    @Override
    public FoxLinkPath getPath(@Nonnull String input) throws FoxCommandException {
        if (input.isEmpty()) return FoxHierarchicalPathImpl.root();

        String[] parts = input.split("/+");

        if(parts.length == 0) return FoxHierarchicalPathImpl.root();

        if(parts[0].isEmpty()) {
            parts = Arrays.copyOfRange(parts, 1, parts.length);
        }

        if(parts.length == 0) return FoxHierarchicalPathImpl.root();

        for(String part : parts){
            if(!nameChecker.isClean(part)){
                throw exceptionFactory.newFoxCommandException("Name \"" + part + "\" is invalid!");
            }
        }

        return FoxHierarchicalPathImpl.of(parts);
    }
}
