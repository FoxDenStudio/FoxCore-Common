package net.foxdenstudio.foxcore.api.path.factory;

import net.foxdenstudio.foxcore.api.path.FoxOptionalPath;

import javax.annotation.Nonnull;

public interface FoxOptionalPathFactory<T extends FoxOptionalPath> extends FoxPathFactory<T>  {

    @Nonnull
    T getEmptyPath();

}
