package net.foxdenstudio.foxcore.api.attribute.holder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxcore.api.attribute.value.FoxAttrValue;

import javax.annotation.Nonnull;
import java.util.*;

public class AttributeContainer implements AttributeHolder {

    private List<AttributeHolder> parents;
    private Map<FoxAttribute<?>, FoxAttrValue<?, ?>> fixedAttributes;
    private Map<FoxAttribute<?>, FoxAttrValue<?, ?>> extraAttributes;

    /**
     * Constructor for including multiple parents. The Collection interface is for convinience only.
     * The iteration order does matter potentially, if parents have the same attribute.
     *
     * @param parents    the parents
     * @param attributes the guaranteed attributes of this container.
     */
    public AttributeContainer(Collection<? extends AttributeHolder> parents, boolean followDelegates, FoxAttribute<?>... attributes) {
        this.fixedAttributes = new HashMap<>();
        this.extraAttributes = new HashMap<>();
        for (FoxAttribute<?> attr : attributes) {
            this.fixedAttributes.put(attr, attr.getValueProvider().get());
        }

        ImmutableList.Builder<AttributeHolder> builder = ImmutableList.builder();
        if(followDelegates){
            for(AttributeHolder parent : parents){
                AttributeHolder actual = parent;
                while (actual instanceof DelegateAttributeHolder){
                    actual = ((DelegateAttributeHolder) actual).getDelegateAttrHolder();
                }
                builder.add(actual);
            }
        } else {
            builder.addAll(parents);
        }
        this.parents = builder.build();
    }

    public AttributeContainer(FoxAttribute<?>... attributes) {
        this(ImmutableList.of(), false, attributes);
    }

    public AttributeContainer(AttributeHolder parent, boolean followDelegates, FoxAttribute<?>... attributes) {
       this(ImmutableList.of(parent), followDelegates, attributes);
    }

    @Nonnull
    @Override
    public Set<FoxAttribute<?>> getParentAttributes() {
        ImmutableSet.Builder<FoxAttribute<?>> builder = ImmutableSet.builder();
        for (AttributeHolder parent : this.parents) {
            builder.addAll(parent.getAttributes());
        }
        return builder.build();
    }

    @Nonnull
    @Override
    public Set<FoxAttribute<?>> getFixedAttributes() {
        return ImmutableSet.copyOf(this.fixedAttributes.keySet());
    }

    @Nonnull
    @Override
    public Set<FoxAttribute<?>> getExtraAttributes() {
        return ImmutableSet.copyOf(this.extraAttributes.keySet());
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    @Override
    public <V extends FoxAttrValue<?, A>, A extends FoxAttribute<V>> Optional<V> getAttrValue(A attribute) {
        V value = (V) this.fixedAttributes.get(attribute);
        if (value == null) {
            value = (V) this.extraAttributes.get(attribute);
        }
        if (value == null) {
            for (AttributeHolder parent : this.parents) {
                Optional<V> optionalV = parent.getAttrValue(attribute);
                if (optionalV.isPresent()) {
                    value = optionalV.get();
                    break;
                }
            }
        }
        return Optional.ofNullable(value);
    }

    @Nonnull
    @Override
    public Optional<FoxAttrValue<?, ?>> getAttrValueWeak(FoxAttribute<?> attribute) {
        FoxAttrValue<?,?> value =  this.fixedAttributes.get(attribute);
        if (value == null) {
            value = this.extraAttributes.get(attribute);
        }
        if (value == null) {
            for (AttributeHolder parent : this.parents) {
                Optional<FoxAttrValue<?,?>> optionalValue = parent.getAttrValueWeak(attribute);
                if (optionalValue.isPresent()) {
                    value = optionalValue.get();
                    break;
                }
            }
        }
        return Optional.ofNullable(value);
    }

    @Nonnull
    @Override
    public <V extends FoxAttrValue<?, A>, A extends FoxAttribute<V>> V getOrCreateAttrValue(A attribute) {
        Optional<V> valueOptional = this.getAttrValue(attribute);
        if (valueOptional.isPresent()) {
            return valueOptional.get();
        } else {
            V value = attribute.getValueProvider().get();
            this.extraAttributes.put(attribute, value);
            return value;
        }
    }

    @Override
    public void setAttrValue(FoxAttrValue<?, ?> value) {
        FoxAttribute<?> attribute = value.getAttribute();
        if (this.fixedAttributes.containsKey(attribute)) {
            this.fixedAttributes.put(attribute, value);
        } else {
            this.extraAttributes.put(attribute, value);
        }
    }

    @Override
    public boolean removeExtraAttribute(FoxAttribute<?> attribute) {
        FoxAttrValue<?, ?> value = this.extraAttributes.remove(attribute);
        return value != null;
    }

    @Override
    public boolean hasAttribute(FoxAttribute<?> attribute) {
        if (this.fixedAttributes.containsKey(attribute) || this.extraAttributes.containsKey(attribute)) return true;
        for (AttributeHolder parent : this.parents) {
            if (parent.hasAttribute(attribute)) return true;
        }
        return false;
    }

    @Nonnull
    @Override
    public List<AttributeHolder> getParents() {
        return ImmutableList.copyOf(parents);
    }
}
