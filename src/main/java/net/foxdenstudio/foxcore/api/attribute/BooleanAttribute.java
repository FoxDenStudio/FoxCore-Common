package net.foxdenstudio.foxcore.api.attribute;

import net.foxdenstudio.foxcore.api.property.FoxProperty;

import javax.inject.Provider;

public abstract class BooleanAttribute<P extends FoxProperty<Boolean, ? extends BooleanAttribute<P>>> extends BaseAttribute<P> {

    protected BooleanAttribute(Provider<P> propertyProvider, String serializedName) {
        super(propertyProvider, serializedName);
    }
}
