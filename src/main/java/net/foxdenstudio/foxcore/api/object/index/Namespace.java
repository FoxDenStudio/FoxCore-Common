package net.foxdenstudio.foxcore.api.object.index;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import java.util.Collection;
import java.util.Optional;

public interface Namespace {

    Optional<FoxObject> getObject(StandardPathComponent path);

    Collection<StandardPathComponent> getAllObjectPaths();

    default boolean isWritable() {
        return this instanceof WritableNamespace;
    }

}
