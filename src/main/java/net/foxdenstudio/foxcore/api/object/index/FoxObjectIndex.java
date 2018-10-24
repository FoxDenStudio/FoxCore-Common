package net.foxdenstudio.foxcore.api.object.index;

import net.foxdenstudio.foxcore.api.path.FoxIndexPath;

import java.util.Optional;

public interface FoxObjectIndex extends Namespace{

    Optional<Namespace> getNamespace(FoxIndexPath indexPath);
}
