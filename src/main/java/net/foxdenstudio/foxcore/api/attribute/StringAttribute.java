package net.foxdenstudio.foxcore.api.attribute;

import net.foxdenstudio.foxcore.api.attribute.value.StringAttrValue;

import javax.inject.Provider;

public abstract class StringAttribute<V extends StringAttrValue<? extends StringAttribute<V>>> extends BaseAttribute<V> {

    private final boolean compareIgnoreCase;

    protected StringAttribute(Provider<V> attrValueProvider, String serializedName, boolean compareIgnoreCase) {
        super(attrValueProvider, serializedName);
        this.compareIgnoreCase = compareIgnoreCase;
    }

    protected StringAttribute(Provider<V> attrValueProvider, String serializedName){
        this(attrValueProvider, serializedName, true);
    }

    public boolean doesCompareIgnoreCase() {
        return compareIgnoreCase;
    }
}
