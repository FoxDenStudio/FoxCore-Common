package net.foxdenstudio.foxcore.api.attribute;

import net.foxdenstudio.foxcore.api.attribute.value.IntegerAttrValue;

import javax.annotation.Nonnull;
import javax.inject.Provider;

public abstract class IntegerAttribute<V extends IntegerAttrValue<? extends IntegerAttribute<V>>> extends BaseAttribute<V> {

    protected IntegerAttribute(@Nonnull Provider<V> attrValueProvider,
                               @Nonnull String serializedName,
                               @Nonnull InheritanceMode inheritanceMode) {
        super(attrValueProvider, serializedName, inheritanceMode);
    }
}
