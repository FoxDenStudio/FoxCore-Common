package net.foxdenstudio.foxcore.api.object.index;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.reference.IndexReference;
import net.foxdenstudio.foxcore.api.path.components.FoxObjectPath;

import java.util.Optional;

public interface WritableNamespace extends Namespace {

    <T extends FoxObject> Optional<IndexReference<T>> addObject(T foxObject, FoxObjectPath path);

}
