package net.foxdenstudio.foxcore.api.path.factory;

import net.foxdenstudio.foxcore.api.path.components.FoxIndexPath;
import net.foxdenstudio.foxcore.api.path.components.FoxNamespacePath;

import javax.annotation.Nonnull;

public interface FoxIndexPathFactory extends FoxOptionalPathFactory<FoxIndexPath> {

    @Nonnull FoxIndexPath getPath(@Nonnull String indexType, @Nonnull FoxNamespacePath namespacePath);
}
