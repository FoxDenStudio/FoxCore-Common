package net.foxdenstudio.foxsuite.foxcore.api.attribute;

import net.foxdenstudio.foxsuite.foxcore.api.attribute.value.StringAttrValue;

import javax.annotation.Nonnull;
import javax.inject.Provider;

public abstract class StringAttribute<V extends StringAttrValue<? extends StringAttribute<V>>> extends BaseAttribute<V> {

    private final boolean compareIgnoreCase;

    protected StringAttribute(@Nonnull Provider<V> attrValueProvider,
                              @Nonnull String serializedName,
                              @Nonnull InheritanceMode inheritanceMode,
                              boolean compareIgnoreCase) {
        super(attrValueProvider, serializedName, inheritanceMode);
        this.compareIgnoreCase = compareIgnoreCase;
    }

    protected StringAttribute(@Nonnull Provider<V> attrValueProvider,
                              @Nonnull String serializedName,
                              @Nonnull InheritanceMode inheritanceMode){
        this(attrValueProvider, serializedName, inheritanceMode, true);
    }

    public boolean doesCompareIgnoreCase() {
        return compareIgnoreCase;
    }
}
