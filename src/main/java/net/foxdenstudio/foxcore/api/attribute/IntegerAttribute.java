package net.foxdenstudio.foxcore.api.attribute;

import net.foxdenstudio.foxcore.api.property.FoxProperty;

import javax.inject.Provider;

public abstract class IntegerAttribute<P extends FoxProperty<Integer, ? extends IntegerAttribute<P>>> extends BaseAttribute<P> {

    protected IntegerAttribute(Provider<P> propertyProvider, String serializedName) {
        super(propertyProvider, serializedName);
    }
}
