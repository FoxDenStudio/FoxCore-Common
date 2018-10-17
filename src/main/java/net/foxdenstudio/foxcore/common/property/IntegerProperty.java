package net.foxdenstudio.foxcore.common.property;

import net.foxdenstudio.foxcore.common.attribute.IntegerAttribute;

public abstract class IntegerProperty<A extends IntegerAttribute<? extends IntegerProperty<A>>>
        extends BaseProperty<Integer, A>
        implements Comparable<IntegerProperty<A>> {

    protected IntegerProperty(A attribute, Integer value) {
        super(attribute, value);
    }

    @Override
    public int compareTo(IntegerProperty<A> o) {
        return this.value.compareTo(o.value);
    }
}
