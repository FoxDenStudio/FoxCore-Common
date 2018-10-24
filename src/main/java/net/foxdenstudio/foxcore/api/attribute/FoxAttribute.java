package net.foxdenstudio.foxcore.api.attribute;

import net.foxdenstudio.foxcore.api.property.FoxProperty;

import javax.inject.Provider;


/**
 * Interface for singleton attribute classes that are used to define properties.
 * Implementors should be Guice injectable singletons for consistency.
 *
 * @param <P> The corresponding property class.
 */
public interface FoxAttribute<P extends FoxProperty> {

    Provider<P> getProvider();

    String getSerializedName();

}
