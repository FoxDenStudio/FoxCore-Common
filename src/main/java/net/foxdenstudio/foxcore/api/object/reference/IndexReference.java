package net.foxdenstudio.foxcore.api.object.reference;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxcore.api.path.components.FoxFullPath;

import java.util.Optional;

public interface IndexReference extends FoxObjectReference {

    @Override
    Optional<FoxObject> getObject();

    Optional<FoxFullPath> getPrimaryPath();

    FoxObjectIndex getIndex();

    boolean stillValid();

    boolean removeObjectFromIndex();
}
