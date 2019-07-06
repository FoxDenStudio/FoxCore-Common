package net.foxdenstudio.foxcore.api.attribute;

import net.foxdenstudio.foxcore.api.attribute.value.BoundedIntegerAttrValue;

import javax.inject.Provider;

public abstract class BoundedIntegerAttribute<V extends BoundedIntegerAttrValue<? extends BoundedIntegerAttribute<V>>> extends IntegerAttribute<V> {

    final int upperBound, lowerBound;

    protected BoundedIntegerAttribute(Provider<V> attrValueProvider, String serializedName, int upperBound, int lowerBound) {
        super(attrValueProvider, serializedName);
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }
}
