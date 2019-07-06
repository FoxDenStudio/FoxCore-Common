package net.foxdenstudio.foxcore.api.attribute;

import net.foxdenstudio.foxcore.api.attribute.value.BooleanAttrValue;
import net.foxdenstudio.foxcore.api.attribute.value.FoxAttrValue;

import javax.inject.Provider;

public abstract class BooleanAttribute<V extends BooleanAttrValue<? extends BooleanAttribute<V>>> extends BaseAttribute<V> {

    protected BooleanAttribute(Provider<V> attrValueProvider, String serializedName) {
        super(attrValueProvider, serializedName);
    }
}
