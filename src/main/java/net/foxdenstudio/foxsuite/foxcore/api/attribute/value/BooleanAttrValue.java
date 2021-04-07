package net.foxdenstudio.foxsuite.foxcore.api.attribute.value;

import net.foxdenstudio.foxsuite.foxcore.api.attribute.BooleanAttribute;

public abstract class BooleanAttrValue<A extends BooleanAttribute<? extends BooleanAttrValue<A>>> extends BaseAttrValue<Boolean, A> {

    protected BooleanAttrValue(A attribute, Boolean initialValue) {
        super(attribute, initialValue);
    }

}
