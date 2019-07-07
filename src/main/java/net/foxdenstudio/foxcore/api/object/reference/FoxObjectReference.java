package net.foxdenstudio.foxcore.api.object.reference;

import net.foxdenstudio.foxcore.api.object.FoxObject;

import java.util.Optional;

public interface FoxObjectReference<T extends FoxObject> {

    Optional<T> getObject();

}
