package net.foxdenstudio.foxcore.impl.path.factory;

import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.path.components.FoxFullPath;
import net.foxdenstudio.foxcore.api.path.components.FoxIndexPath;
import net.foxdenstudio.foxcore.api.path.factory.FoxFullPathFactory;
import net.foxdenstudio.foxcore.api.path.factory.FoxIndexPathFactory;
import net.foxdenstudio.foxcore.api.path.factory.FoxLinkPathFactory;
import net.foxdenstudio.foxcore.api.path.factory.FoxObjectPathFactory;

import javax.inject.Inject;
import javax.inject.Singleton;
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
        String indexPathString = "";

        if(input.startsWith("@")){
            input = input.substring(1);
            String[] parts = input.split(":+", 2);

            if(parts.length < 2 || parts[1].isEmpty())
                throw this.exceptionFactory.newFoxCommandException("Must include object path!");

            indexPathString = parts[0];
            input = parts[1];
        }

        String[] parts = input.split(":+");
        String objectPathString = parts[0];




        return null;
    }

}
