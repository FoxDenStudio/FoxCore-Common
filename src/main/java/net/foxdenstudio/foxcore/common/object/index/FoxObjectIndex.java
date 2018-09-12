package net.foxdenstudio.foxcore.common.object.index;

import net.foxdenstudio.foxcore.common.object.FoxObject;

import java.util.Optional;

public interface FoxObjectIndex<T extends FoxObject> {

    Optional<Namespace<T>> getNamespace(String... elements);
}
