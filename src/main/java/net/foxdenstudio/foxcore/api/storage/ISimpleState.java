package net.foxdenstudio.foxcore.api.storage;

import net.foxdenstudio.foxcore.api.object.FoxObject;

import javax.annotation.Nullable;

/**
 * Capability interface for fox objects representing the ability
 * to get and set the entire state of an object through a data class (a struct)
 *
 * This data class should only store data and not any references to larger objects,
 * as it may be used in reflective mapping procedures and ORMs.
 * The data class must have the @{@link FoxStorageDataClass} annotation
 *
 * by implementing this interface, objects immediately gain various capabilities such as
 * easy copying and moving between resource locations, as well as the support of multiple
 * resource location types.
 *
 * This interface should be implemented as much as possible to reduce the amount of code needed.
 *
 * @param <D> the data class
 */
public interface ISimpleState<D extends FoxObjectData> extends FoxObject {

    /**
     * Gets a copy of the current state as the data class. This state should be complete.
     *
     * @return the populated data class instance
     */
    D getData();

    /**
     * Sets the object's state to that of the data class.
     * This effectively rewrites the current running configuration.
     * Passing in null should reset the object to its default config.
     *
     * This is what would be called in the case of a reload.
     *
     * @param data the state the object should be set to.
     */
    boolean setData(@Nullable D data);

}
