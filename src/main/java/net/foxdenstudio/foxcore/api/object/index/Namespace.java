package net.foxdenstudio.foxcore.api.object.index;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.path.components.FoxIndexPath;
import net.foxdenstudio.foxcore.api.path.components.FoxNamespacePath;
import net.foxdenstudio.foxcore.api.path.components.FoxObjectPath;

import java.util.Collection;
import java.util.Optional;

public interface Namespace {

    Optional<FoxObject> getObject(FoxObjectPath path);

    Collection<FoxObjectPath> getAllPaths();

    FoxIndexPath getPath();

}
