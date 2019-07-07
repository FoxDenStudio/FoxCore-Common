package net.foxdenstudio.foxcore.api.path.factory;

import net.foxdenstudio.foxcore.api.path.components.FoxFullPath;
import net.foxdenstudio.foxcore.api.path.components.FoxIndexPath;
import net.foxdenstudio.foxcore.api.path.components.FoxObjectPath;

import javax.annotation.Nonnull;

public interface FoxFullPathFactory extends FoxPathFactory<FoxFullPath> {

    @Nonnull
    FoxFullPath getPath(@Nonnull FoxIndexPath indexPath, @Nonnull FoxObjectPath objectPath);
}
