package net.foxdenstudio.foxcore.api.object.reference;

import java.util.Optional;

public interface IndirectReference extends FoxObjectReference {

    Optional<FoxObjectReference> getActualReference();
}
