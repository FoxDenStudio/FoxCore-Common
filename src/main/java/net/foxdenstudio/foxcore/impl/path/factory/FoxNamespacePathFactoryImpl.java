package net.foxdenstudio.foxcore.impl.path.factory;

import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.path.components.FoxNamespacePath;
import net.foxdenstudio.foxcore.api.path.factory.FoxNamespacePathFactory;
import net.foxdenstudio.foxcore.api.util.NameChecker;
import net.foxdenstudio.foxcore.impl.path.FoxHierarchicalPathImpl;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.Optional;

@Singleton
public class FoxNamespacePathFactoryImpl extends FoxPathFactoryBaseImpl implements FoxNamespacePathFactory {

    @Inject
    private FoxNamespacePathFactoryImpl() {
    }

    @SuppressWarnings("Duplicates")
    @Override
    public FoxNamespacePath getPath(@Nonnull String input) throws FoxCommandException {
        if (input.isEmpty()) return this.getEmptyPath();

        String[] parts = input.split("/+");

        if(parts.length == 0) return this.getEmptyPath();

        if(parts[0].isEmpty()) {
            parts = Arrays.copyOfRange(parts, 1, parts.length);
        }

        if(parts.length == 0) return this.getEmptyPath();

        for(String part : parts){
            if(!nameChecker.isClean(part)){
                throw exceptionFactory.newFoxCommandException("Name \"" + part + "\" is invalid!");
            }
        }

        return FoxHierarchicalPathImpl.of(parts);
    }

    @Nonnull
    @Override
    public FoxNamespacePath getEmptyPath() {
        return FoxHierarchicalPathImpl.root();
    }
}
