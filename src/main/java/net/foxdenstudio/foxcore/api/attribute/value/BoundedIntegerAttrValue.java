package net.foxdenstudio.foxcore.api.attribute.value;

import net.foxdenstudio.foxcore.api.attribute.BoundedIntegerAttribute;

public abstract class BoundedIntegerAttrValue<A extends BoundedIntegerAttribute<? extends BoundedIntegerAttrValue<A>>> extends IntegerAttrValue<A> {

    protected BoundedIntegerAttrValue(A attribute, Integer value) {
        super(attribute, value);
    }
}
