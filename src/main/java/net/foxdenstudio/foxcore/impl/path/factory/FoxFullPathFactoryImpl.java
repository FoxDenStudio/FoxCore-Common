package net.foxdenstudio.foxcore.impl.path.factory;

import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.path.components.FoxFullPath;
import net.foxdenstudio.foxcore.api.path.components.FoxIndexPath;
import net.foxdenstudio.foxcore.api.path.components.FoxLinkPath;
import net.foxdenstudio.foxcore.api.path.components.FoxObjectPath;
import net.foxdenstudio.foxcore.api.path.factory.FoxFullPathFactory;
import net.foxdenstudio.foxcore.api.path.factory.FoxIndexPathFactory;
import net.foxdenstudio.foxcore.api.path.factory.FoxLinkPathFactory;
import net.foxdenstudio.foxcore.api.path.factory.FoxObjectPathFactory;
import net.foxdenstudio.foxcore.impl.path.FoxFullPathImpl;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class FoxFullPathFactoryImpl extends FoxPathFactoryBaseImpl implements FoxFullPathFactory {

    private final FoxIndexPathFactory indexPathFactory;
    private final FoxObjectPathFactory objectPathFactory;
    private final FoxLinkPathFactory linkPathFactory;

    @Inject
    private FoxFullPathFactoryImpl(
            FoxIndexPathFactory indexPathFactory,
            FoxObjectPathFactory objectPathFactory,
            FoxLinkPathFactory linkPathFactory) {
        this.indexPathFactory = indexPathFactory;
        this.objectPathFactory = objectPathFactory;
        this.linkPathFactory = linkPathFactory;
    }


    @Override
    public FoxFullPath getPath(String input) throws FoxCommandException {
        FoxIndexPath indexPath;

        if(input.startsWith("@")){
            input = input.substring(1);
            String[] parts = input.split(":+", 2);

            indexPath = this.indexPathFactory.getPath(parts[0]);

            if(parts.length < 2 || parts[1].isEmpty())
                throw new FoxCommandException("Must include object path!");

            input = parts[1];
        } else {
            indexPath = this.indexPathFactory.getPath("");
        }

        String[] parts = input.split(":+");
        FoxObjectPath objectPath = this.objectPathFactory.getPath(parts[0]);

        List<FoxLinkPath> linkPaths = new ArrayList<>();

        for (int i = 1; i < parts.length; i++) {
            linkPaths.add(this.linkPathFactory.getPath(parts[i]));
        }

        return FoxFullPathImpl.of(indexPath, objectPath, linkPaths);
    }

    @Nonnull
    @Override
    public FoxFullPath getPath(@Nonnull FoxIndexPath indexPath, @Nonnull FoxObjectPath objectPath) {
        return FoxFullPathImpl.of(indexPath, objectPath);
    }
}
