package net.foxdenstudio.foxcore.api.attribute.holder;

import com.google.common.collect.ImmutableSet;
import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxcore.api.attribute.value.FoxAttrValue;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AttributeHolder {

    /**
     * Get parent attributes, if any.
     * @return parents.
     */
    @Nonnull
    Set<FoxAttribute<?>> getParentAttributes();

    @Nonnull
    Set<FoxAttribute<?>> getFixedAttributes();

    @Nonnull
    Set<FoxAttribute<?>> getExtraAttributes();

    @Nonnull
    default Set<FoxAttribute<?>> getAttributes() {
        ImmutableSet.Builder<FoxAttribute<?>> builder = ImmutableSet.builder();
        builder.addAll(this.getFixedAttributes());
        builder.addAll(this.getExtraAttributes());
        builder.addAll(this.getParentAttributes());
        return builder.build();
    }

    @Nonnull
    <V extends FoxAttrValue<?, A>, A extends FoxAttribute<V>> Optional<V> getAttrValue(A attribute);

    @Nonnull
    <V extends FoxAttrValue<?, A>, A extends FoxAttribute<V>> V getOrCreateAttrValue(A attribute);

    void setAttrValue(FoxAttrValue<?, ?> value);

    boolean removeExtraAttribute(FoxAttribute<?> attribute);

    boolean hasAttribute(FoxAttribute<?> attribute);

    @Nonnull
    List<AttributeHolder> getParents();
}
