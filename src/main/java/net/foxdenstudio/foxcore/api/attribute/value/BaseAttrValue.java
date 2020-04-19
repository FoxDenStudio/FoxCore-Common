package net.foxdenstudio.foxcore.api.attribute.value;

import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;

import javax.annotation.Nonnull;

public abstract class BaseAttrValue<T, A extends FoxAttribute<? extends FoxAttrValue<T, A>>> implements FoxAttrValue<T, A> {

    transient protected A attribute;
    protected T value;

    protected BaseAttrValue(A attribute, T initialValue) {
        this.attribute = attribute;
        this.value = initialValue;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public void set(T value) {
        this.value = value;
    }

    @Nonnull
    @Override
    public A getAttribute() {
        return attribute;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
