package net.foxdenstudio.foxsuite.foxcore.api.attribute;

import net.foxdenstudio.foxsuite.foxcore.api.attribute.value.BooleanAttrValue;

import javax.annotation.Nonnull;
import javax.inject.Provider;

public abstract class BooleanAttribute<V extends BooleanAttrValue<? extends BooleanAttribute<V>>> extends BaseAttribute<V> {

    protected BooleanAttribute(Provider<V> attrValueProvider, String serializedName, @Nonnull InheritanceMode inheritanceMode) {
        super(attrValueProvider, serializedName, inheritanceMode);
    }
}
