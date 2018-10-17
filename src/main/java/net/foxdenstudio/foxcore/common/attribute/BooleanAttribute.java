package net.foxdenstudio.foxcore.common.attribute;

import net.foxdenstudio.foxcore.common.property.FoxProperty;

import javax.inject.Provider;

public abstract class BooleanAttribute<P extends FoxProperty<Boolean, ? extends BooleanAttribute<P>>> extends BaseAttribute<P> {

    protected BooleanAttribute(Provider<P> propertyProvider, String serializedName) {
        super(propertyProvider, serializedName);
    }
}
