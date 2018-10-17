package net.foxdenstudio.foxcore.common.attribute;

import net.foxdenstudio.foxcore.common.property.FoxProperty;

import javax.inject.Provider;

public abstract class BoundedIntegerAttribute<P extends FoxProperty<Integer, ? extends BoundedIntegerAttribute<P>>> extends BaseAttribute<P> {

    final int upperBound, lowerBound;

    protected BoundedIntegerAttribute(Provider<P> propertyProvider, String serializedName, int upperBound, int lowerBound) {
        super(propertyProvider, serializedName);
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }
}
