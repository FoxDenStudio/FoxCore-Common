package net.foxdenstudio.foxsuite.foxcore.api.object.reference.types;

import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPath;

import java.util.Optional;

/**
 * Lazy references are references that maybe be evaluable at a later time.
 */
public interface LazyReference extends ProxyReference {

    /**
     * Returns the path that was recorded for the purpose of locating this object.
     * @return the recorded path for this object.
     */
    FoxPath getRecordedPath();

    /**
     * Gets actual path to object. Only returns a non-empty value if object can be located.
     *
     * @return the actual path to the object, if it can be located.
     */
    Optional<FoxPath> getActualPath();
}
