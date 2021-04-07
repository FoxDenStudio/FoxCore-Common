package net.foxdenstudio.foxsuite.foxcore.api.attribute.holder;

import com.google.common.collect.ImmutableSet;
import net.foxdenstudio.foxsuite.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxsuite.foxcore.api.attribute.value.FoxAttrValue;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AttributeHolder {

    /**
     * Get parent attributes, if any.
     *
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
        this.getParentAttributes().stream()
                .filter(attr -> attr.getInheritanceMode() != FoxAttribute.InheritanceMode.NONE)
                .forEach(builder::add);
        return builder.build();
    }

    /**
     * Gets an attribute value for the given attribute.
     *
     * This is a generically typed method to make it compile convenient to use typed attributes as keys
     * when doing lookups in code as part of using them.
     *
     * By default this calls {@link #getAttrValueWeak(FoxAttribute)} and casts, doing no additional checks.
     *
     * @param attribute the attribute to get a value for
     * @param <V> the type of the value
     * @param <A> the type of the attribute
     * @return an attribute value for the given attribute
     */

    @Nonnull
    <V extends FoxAttrValue<?, A>, A extends FoxAttribute<V>> Optional<V> getAttrValue(A attribute);

    /**
     * Gets an attribute value for the given attribute.
     *
     * This method is not generic, however it is still required to pair attributes to their correct attribute values,
     * even when generics aren't available.
     *
     * @param attribute the attribute to get a value for
     * @return an attribute value for the given attribute
     */
    @Nonnull
    Optional<FoxAttrValue<?, ?>> getAttrValueWeak(FoxAttribute<?> attribute);

    @Nonnull
    <V extends FoxAttrValue<?, A>, A extends FoxAttribute<V>> V getOrCreateAttrValue(A attribute);

    void setAttrValue(FoxAttrValue<?, ?> value);

    boolean removeExtraAttribute(FoxAttribute<?> attribute);

    boolean hasAttribute(FoxAttribute<?> attribute);

    @Nonnull
    List<AttributeHolder> getParents();
}
