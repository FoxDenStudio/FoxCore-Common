package net.foxdenstudio.foxsuite.foxcore.api.attribute.value;

import net.foxdenstudio.foxsuite.foxcore.api.attribute.StringAttribute;

import javax.annotation.Nonnull;

public abstract class StringAttrValue<A extends StringAttribute<? extends StringAttrValue<A>>>
        extends BaseAttrValue<String, A>
        implements Comparable<StringAttrValue<A>> {

    protected StringAttrValue(A attribute, String initialValue) {
        super(attribute, initialValue);
    }

    @Override
    public int compareTo(@Nonnull StringAttrValue<A> o) {
        if (this.attribute.doesCompareIgnoreCase()) {
            return this.value.compareToIgnoreCase(o.value);
        } else {
            return this.value.compareTo(o.value);
        }
    }
}
