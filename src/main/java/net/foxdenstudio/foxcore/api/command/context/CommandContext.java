package net.foxdenstudio.foxcore.api.command.context;

import net.foxdenstudio.foxcore.api.exception.command.FoxCommandException;
import net.foxdenstudio.foxcore.api.object.index.Namespace;
import net.foxdenstudio.foxcore.api.object.reference.IndexReference;
import net.foxdenstudio.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxcore.api.path.FoxPathExt;

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
