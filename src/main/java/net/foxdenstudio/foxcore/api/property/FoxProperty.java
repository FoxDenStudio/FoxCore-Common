package net.foxdenstudio.foxcore.api.property;

import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;

import javax.annotation.Nonnull;

public interface FoxProperty<T, A extends FoxAttribute<? extends FoxProperty<T, A>>> {

    T get();

    void set(T value);

    @Nonnull
    A getAttribute();

}
