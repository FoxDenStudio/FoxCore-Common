package net.foxdenstudio.foxcore.common.storage;

import net.foxdenstudio.foxcore.common.object.FoxObject;

public interface FoxSerializer<T extends FoxObject, U> {

    void serialize(T object, U location);

    T deserialize(U location);

}
