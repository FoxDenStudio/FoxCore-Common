package net.foxdenstudio.foxcore.common.property;

import net.foxdenstudio.foxcore.common.attribute.FoxAttribute;

import javax.annotation.Nonnull;

public interface FoxProperty<T, A extends FoxAttribute<? extends FoxProperty<T, A>>> {

    T get();

    void set(T value);

    @Nonnull
    A getAttribute();

}
