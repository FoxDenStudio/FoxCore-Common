package net.foxdenstudio.foxcore.api.attribute.value;

import net.foxdenstudio.foxcore.api.attribute.IntegerAttribute;

public abstract class IntegerAttrValue<A extends IntegerAttribute<? extends IntegerAttrValue<A>>>
        extends BaseAttrValue<Integer, A>
        implements Comparable<IntegerAttrValue<A>> {

    protected IntegerAttrValue(A attribute, Integer value) {
        super(attribute, value);
    }

    @Override
    public int compareTo(IntegerAttrValue<A> o) {
        return this.value.compareTo(o.value);
    }
}
