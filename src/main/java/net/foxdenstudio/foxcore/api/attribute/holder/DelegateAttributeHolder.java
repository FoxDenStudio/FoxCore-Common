package net.foxdenstudio.foxcore.api.attribute.holder;

import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxcore.api.attribute.value.FoxAttrValue;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DelegateAttributeHolder extends AttributeHolder {

    @Nonnull
    @Override
    default Set<FoxAttribute<?>> getParentAttributes() {
        return this.getDelegate().getParentAttributes();
    }

    @Nonnull
    @Override
    default Set<FoxAttribute<?>> getFixedAttributes() {
        return this.getDelegate().getFixedAttributes();
    }

    @Nonnull
    @Override
    default Set<FoxAttribute<?>> getExtraAttributes() {
        return this.getDelegate().getExtraAttributes();
    }

    @Nonnull
    @Override
    default <V extends FoxAttrValue<?, A>, A extends FoxAttribute<V>> Optional<V> getAttrValue(A attribute) {
        return this.getDelegate().getAttrValue(attribute);
    }

    @Nonnull
    @Override
    default <V extends FoxAttrValue<?, A>, A extends FoxAttribute<V>> V getOrCreateAttrValue(A attribute) {
        return this.getDelegate().getOrCreateAttrValue(attribute);
    }

    @Override
    default void setAttrValue(FoxAttrValue<?, ?> value) {
        this.getDelegate().setAttrValue(value);
    }

    @Override
    default boolean removeExtraAttribute(FoxAttribute<?> attribute) {
        return this.getDelegate().removeExtraAttribute(attribute);
    }

    @Override
    default boolean hasAttribute(FoxAttribute<?> attribute) {
        return this.getDelegate().hasAttribute(attribute);
    }

    @Nonnull
    @Override
    default List<AttributeHolder> getParents() {
        return this.getDelegate().getParents();
    }

    AttributeHolder getDelegate();
}
