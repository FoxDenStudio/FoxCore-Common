package net.foxdenstudio.foxcore.api.storage;

import net.foxdenstudio.foxcore.api.object.FoxObject;

public interface FoxSerializer<T extends FoxObject, U> {

    void serialize(T object, U location);

    T deserialize(U location);

}
