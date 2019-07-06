package net.foxdenstudio.foxcore.api.attribute;

import net.foxdenstudio.foxcore.api.attribute.value.IntegerAttrValue;

import javax.inject.Provider;

public abstract class IntegerAttribute<V extends IntegerAttrValue<? extends IntegerAttribute<V>>> extends BaseAttribute<V> {

    protected IntegerAttribute(Provider<V> attrValueProvider, String serializedName) {
        super(attrValueProvider, serializedName);
    }
}
