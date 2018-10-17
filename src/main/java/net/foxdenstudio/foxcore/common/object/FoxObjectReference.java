package net.foxdenstudio.foxcore.common.object;

import java.util.Optional;

public interface FoxObjectReference<T extends FoxObject> {

    Optional<T> getObject();


}
