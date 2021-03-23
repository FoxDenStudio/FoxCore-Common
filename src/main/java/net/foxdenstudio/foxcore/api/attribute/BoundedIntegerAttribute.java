package net.foxdenstudio.foxcore.api.attribute;

import net.foxdenstudio.foxcore.api.attribute.value.BoundedIntegerAttrValue;

import javax.annotation.Nonnull;
import javax.inject.Provider;

public abstract class BoundedIntegerAttribute<V extends BoundedIntegerAttrValue<? extends BoundedIntegerAttribute<V>>> extends IntegerAttribute<V> {

    final int upperBound, lowerBound;

    protected BoundedIntegerAttribute(@Nonnull Provider<V> attrValueProvider,
                                      @Nonnull String serializedName,
                                      @Nonnull InheritanceMode inheritanceMode,
                                      int upperBound, int lowerBound) {
        super(attrValueProvider, serializedName, inheritanceMode);
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }
}
