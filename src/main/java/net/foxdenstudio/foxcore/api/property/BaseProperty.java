package net.foxdenstudio.foxcore.api.property;

import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;

import javax.annotation.Nonnull;

public abstract class BaseProperty<T, A extends FoxAttribute<? extends FoxProperty<T, A>>> implements FoxProperty<T, A> {

    protected A attribute;
    protected T value;

    protected BaseProperty(A attribute, T initialValue) {
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
}
