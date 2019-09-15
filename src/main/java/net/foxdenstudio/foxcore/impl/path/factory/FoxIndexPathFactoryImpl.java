package net.foxdenstudio.foxcore.impl.path.factory;

import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.path.components.FoxIndexPath;
import net.foxdenstudio.foxcore.api.path.components.FoxNamespacePath;
import net.foxdenstudio.foxcore.api.path.factory.FoxIndexPathFactory;
import net.foxdenstudio.foxcore.api.path.factory.FoxNamespacePathFactory;
import net.foxdenstudio.foxcore.impl.path.FoxIndexPathImpl;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FoxIndexPathFactoryImpl extends FoxPathFactoryBaseImpl implements FoxIndexPathFactory {

    private final FoxNamespacePathFactory namespacePathFactory;

    @Inject
    private FoxIndexPathFactoryImpl(FoxNamespacePathFactory namespacePathFactory) {
        this.namespacePathFactory = namespacePathFactory;
    }

    @Nonnull
    @Override
    public FoxIndexPath getPath(@Nonnull String input) throws FoxCommandException {
        String[] prefixSplit = input.split("/+", 2);
        if (prefixSplit[0].isEmpty() && prefixSplit.length > 1) {
            input = prefixSplit[1];
        }
        if (input.isEmpty()) return this.getEmptyPath();

        String[] parts = input.split("/+", 2);
        String indexType = parts[0];

        if (!indexType.isEmpty() && !nameChecker.isClean(indexType))
            throw exceptionFactory.newFoxCommandException("Name \"" + indexType + "\" is invalid!");

        String namespaceString = "";

        if (parts.length > 1 && !parts[1].isEmpty()) {
            namespaceString = parts[1];
        }

        return FoxIndexPathImpl.of(indexType, this.namespacePathFactory.getPath(namespaceString));
    }

    @Nonnull
    @Override
    public FoxIndexPath getEmptyPath() {
        return FoxIndexPathImpl.of("", this.namespacePathFactory.getEmptyPath());
    }

    @Nonnull
    @Override
    public FoxIndexPath getPath(@Nonnull String indexType, @Nonnull FoxNamespacePath namespacePath) {
        if (indexType.isEmpty() && !namespacePath.isEmpty())
            throw new IllegalArgumentException("Type cannot be empty if namespace path is not empty");
        return FoxIndexPathImpl.of(indexType, namespacePath);
    }
}
