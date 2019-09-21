package net.foxdenstudio.foxcore.impl.path.factory;

import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.path.components.FoxLinkPath;
import net.foxdenstudio.foxcore.api.path.factory.FoxLinkPathFactory;
import net.foxdenstudio.foxcore.impl.path.FoxHierarchicalPathImpl;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;

@Singleton
public class FoxLinkPathFactoryImpl extends FoxPathFactoryBaseImpl implements FoxLinkPathFactory {

    @Inject
    private FoxLinkPathFactoryImpl(){}

    @SuppressWarnings("Duplicates")
    @Override
    public FoxLinkPath getPath(@Nonnull String input) throws FoxCommandException {
        if (input.isEmpty()) return this.getEmptyPath();

        String[] parts = input.split("/+");

        if(parts.length == 0) return this.getEmptyPath();

        if(parts[0].isEmpty()) {
            parts = Arrays.copyOfRange(parts, 1, parts.length);
        }

        if(parts.length == 0) return this.getEmptyPath();

        for(String part : parts){
            if(!nameChecker.isClean(part)){
                throw new FoxCommandException("Name \"" + part + "\" is invalid!");
            }
        }

        return FoxHierarchicalPathImpl.of(parts);
    }

    @Nonnull
    @Override
    public FoxLinkPath getEmptyPath() {
        return FoxHierarchicalPathImpl.root();
    }
}
