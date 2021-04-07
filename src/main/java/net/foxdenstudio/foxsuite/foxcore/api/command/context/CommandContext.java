package net.foxdenstudio.foxsuite.foxcore.api.command.context;

import net.foxdenstudio.foxsuite.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.Namespace;
import net.foxdenstudio.foxsuite.foxcore.api.object.reference.types.IndexReference;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPath;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface CommandContext {

    FoxPath getCurrentPath();

    FoxPath resetPath();

    FoxPath changePath(FoxPath path) throws FoxCommandException;

    Namespace getNamespace(@Nullable FoxPath path) throws FoxCommandException;

    Namespace getNamespaceDirect(@Nonnull FoxPath path) throws FoxCommandException;

    IndexReference getObjectFromIndex(@Nullable FoxPath path) throws FoxCommandException;

    IndexReference getObjectFromIndexDirect(@Nonnull FoxPath path) throws FoxCommandException;
}
