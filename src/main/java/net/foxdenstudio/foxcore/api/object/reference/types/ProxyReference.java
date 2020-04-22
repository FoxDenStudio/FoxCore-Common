package net.foxdenstudio.foxcore.api.object.reference.types;

import net.foxdenstudio.foxcore.api.object.reference.FoxObjectReference;

import java.util.Optional;

public interface ProxyReference extends FoxObjectReference {

    Optional<FoxObjectReference> getTargetReference();
}
