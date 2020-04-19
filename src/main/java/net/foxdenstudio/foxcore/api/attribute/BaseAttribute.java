package net.foxdenstudio.foxcore.api.attribute;

import net.foxdenstudio.foxcore.api.attribute.value.BaseAttrValue;

import javax.inject.Provider;

public abstract class BaseAttribute<V extends BaseAttrValue<?, ? extends BaseAttribute<V>>> implements FoxAttribute<V> {

    private final Provider<V> attrValueProvider;
    private final String serializedName;

    protected BaseAttribute(Provider<V> attrValueProvider, String serializedName) {
        this.attrValueProvider = attrValueProvider;
        this.serializedName = serializedName;
    }

    @Override
    public Provider<V> getValueProvider() {
        return attrValueProvider;
    }

    @Override
    public String getSerializedName() {
        return serializedName;
    }

    @Override
    public String toString() {
        return getSerializedName();
    }
}
