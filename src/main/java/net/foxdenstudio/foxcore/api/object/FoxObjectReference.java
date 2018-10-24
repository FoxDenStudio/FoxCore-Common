package net.foxdenstudio.foxcore.api.object;

import java.util.Optional;

public interface FoxObjectReference<T extends FoxObject> {

    Optional<T> getObject();




}
