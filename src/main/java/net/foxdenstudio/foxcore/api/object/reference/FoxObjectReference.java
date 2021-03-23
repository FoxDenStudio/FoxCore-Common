package net.foxdenstudio.foxcore.api.object.reference;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.reference.types.IndexReference;

import java.util.Optional;

/**
 * A pointer/reference to a Fox Object. Used in many placess.
 */
public interface FoxObjectReference {

    Optional<FoxObject> getObject();

    default Optional<IndexReference> getPrimeReference(){
        return this.getObject().flatMap(FoxObject::getIndexReference);
    }

    /**
     * Whether this reference is still "live", in that it currently holds a reference to an object,
     * or will hold a reference to an object in the future.
     *
     * A reference generally only refers to one object at a time, over. There's nothing stopping a developer from
     * changing the reference, but this might cause some odd behavior depending on usage.
     *
     * When a reference is no longer valid, this usually means the relationship that the reference represented
     * has changed or no longer exists.
     *
     * @return whether this reference is still live.
     */
    boolean isValid();
}
