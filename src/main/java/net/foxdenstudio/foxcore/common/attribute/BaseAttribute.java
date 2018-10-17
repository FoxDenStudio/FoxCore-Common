package net.foxdenstudio.foxcore.common.attribute;

import net.foxdenstudio.foxcore.common.property.FoxProperty;

import javax.inject.Provider;

public abstract class BaseAttribute<P extends FoxProperty> implements FoxAttribute<P> {

    private final Provider<P> propertyProvider;
    private final String serializedName;

    protected BaseAttribute(Provider<P> propertyProvider, String serializedName) {
        this.propertyProvider = propertyProvider;
        this.serializedName = serializedName;
    }

    @Override
    public Provider<P> getProvider() {
        return propertyProvider;
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }
}
