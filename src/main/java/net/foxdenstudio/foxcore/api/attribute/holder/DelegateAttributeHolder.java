package net.foxdenstudio.foxcore.api.attribute.holder;

import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxcore.api.attribute.value.FoxAttrValue;
import net.foxdenstudio.foxcore.api.object.link.LinkContainer;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DelegateAttributeHolder extends AttributeHolder {

    @Nonnull
    @Override
    default Set<FoxAttribute<?>> getParentAttributes() {
        return this.getDelegateAttrHolder().getParentAttributes();
    }

    @Nonnull
    @Override
    default Set<FoxAttribute<?>> getFixedAttributes() {
        return this.getDelegateAttrHolder().getFixedAttributes();
    }

    @Nonnull
    @Override
    default Set<FoxAttribute<?>> getExtraAttributes() {
        return this.getDelegateAttrHolder().getExtraAttributes();
    }

    @Nonnull
    @Override
    default <V extends FoxAttrValue<?, A>, A extends FoxAttribute<V>> Optional<V> getAttrValue(A attribute) {
        return this.getDelegateAttrHolder().getAttrValue(attribute);
    }

    @Nonnull
    @Override
    default Optional<FoxAttrValue<?, ?>> getAttrValueWeak(FoxAttribute<?> attribute) {
        return this.getDelegateAttrHolder().getAttrValueWeak(attribute);
    }

    @Nonnull
    @Override
    default <V extends FoxAttrValue<?, A>, A extends FoxAttribute<V>> V getOrCreateAttrValue(A attribute) {
        return this.getDelegateAttrHolder().getOrCreateAttrValue(attribute);
    }

    @Override
    default void setAttrValue(FoxAttrValue<?, ?> value) {
        this.getDelegateAttrHolder().setAttrValue(value);
    }

    @Override
    default boolean removeExtraAttribute(FoxAttribute<?> attribute) {
        return this.getDelegateAttrHolder().removeExtraAttribute(attribute);
    }

    @Override
    default boolean hasAttribute(FoxAttribute<?> attribute) {
        return this.getDelegateAttrHolder().hasAttribute(attribute);
    }

    @Nonnull
    @Override
    default List<AttributeHolder> getParents() {
        return this.getDelegateAttrHolder().getParents();
    }

    AttributeHolder getDelegateAttrHolder();
}
