package net.foxdenstudio.foxcore.api.attribute;

import net.foxdenstudio.foxcore.api.property.FoxProperty;

import javax.inject.Provider;

public abstract class BoundedIntegerAttribute<P extends FoxProperty<Integer, ? extends BoundedIntegerAttribute<P>>> extends BaseAttribute<P> {

    final int upperBound, lowerBound;

    protected BoundedIntegerAttribute(Provider<P> propertyProvider, String serializedName, int upperBound, int lowerBound) {
        super(propertyProvider, serializedName);
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }
}
