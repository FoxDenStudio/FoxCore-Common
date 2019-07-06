package net.foxdenstudio.foxcore.api.attribute;

import net.foxdenstudio.foxcore.api.attribute.value.FoxAttrValue;

import javax.inject.Provider;


/**
 * Interface for singleton attribute classes that are used to define properties.
 * Implementors should be Guice injectable singletons for consistency.
 *
 * Splish splash smash cronch, dive zoom rudder-butt.
 *
 * @param <V> The corresponding AttrValue class.
 */
public interface FoxAttribute<V extends FoxAttrValue<?, ? extends FoxAttribute<V>>> {

    Provider<V> getValueProvider();

    String getSerializedName();

}
