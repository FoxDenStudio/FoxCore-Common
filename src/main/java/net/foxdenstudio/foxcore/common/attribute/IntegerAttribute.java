package net.foxdenstudio.foxcore.common.attribute;

import net.foxdenstudio.foxcore.common.property.FoxProperty;

import javax.inject.Provider;

public abstract class IntegerAttribute<P extends FoxProperty<Integer, ? extends IntegerAttribute<P>>> extends BaseAttribute<P> {

    protected IntegerAttribute(Provider<P> propertyProvider, String serializedName) {
        super(propertyProvider, serializedName);
    }
}
