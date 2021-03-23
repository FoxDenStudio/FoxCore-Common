package net.foxdenstudio.foxcore.api.object.reference.types;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.reference.FoxObjectReference;

import java.util.Optional;

public interface ProxyReference extends FoxObjectReference {

    Optional<FoxObjectReference> getTargetReference();

    @Override
    default Optional<FoxObject> getObject() {
        return this.getTargetReference().flatMap(FoxObjectReference::getObject);
    }

    @Override
    default boolean isValid() {
        Optional<FoxObjectReference> target = this.getTargetReference();
        return target.isPresent() && target.get().isValid();
    }
}
