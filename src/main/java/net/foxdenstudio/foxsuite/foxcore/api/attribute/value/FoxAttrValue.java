package net.foxdenstudio.foxsuite.foxcore.api.attribute.value;

import net.foxdenstudio.foxsuite.foxcore.api.attribute.FoxAttribute;

import javax.annotation.Nonnull;

public interface FoxAttrValue<T, A extends FoxAttribute<? extends FoxAttrValue<T, A>>> {

    T get();

    void set(T value);

    @Nonnull
    A getAttribute();

}
