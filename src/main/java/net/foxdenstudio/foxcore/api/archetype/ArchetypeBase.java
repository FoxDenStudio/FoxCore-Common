package net.foxdenstudio.foxcore.api.archetype;

import com.google.common.collect.ImmutableList;
import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxcore.api.attribute.holder.AttributeContainer;
import net.foxdenstudio.foxcore.api.attribute.holder.DelegateAttributeHolder;
import net.foxdenstudio.foxcore.content.attribute.ArchetypeDisplayNameAttribute;
import net.foxdenstudio.foxcore.content.attribute.value.ArchetypeDisplayNameAttrValue;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public abstract class ArchetypeBase implements FoxArchetype, DelegateAttributeHolder {

    protected final AttributeContainer attributeContainer;
    protected final List<FoxArchetype> parents;
    protected final String type;
    protected final String name;

    private transient Set<FoxArchetype> allParents = null;
    private transient Set<FoxArchetype> allArchetypes = null;

    protected ArchetypeBase(@Nonnull String type, @Nonnull String name, @Nonnull Collection<FoxArchetype> parents, @Nonnull FoxAttribute<?>... attributes) {

        this.type = type;
        this.name = name;
        this.attributeContainer = new AttributeContainer(parents, true, attributes);
        ImmutableList.Builder<FoxArchetype> builder = ImmutableList.builder();
        builder.addAll(parents);
        this.parents = builder.build();
    }

    protected ArchetypeBase(@Nonnull String type, @Nonnull String name, @Nonnull FoxArchetype parent, @Nonnull FoxAttribute<?>... attributes) {
        this(type, name, ImmutableList.of(parent), attributes);
    }

    protected ArchetypeBase(@Nonnull String type, @Nonnull String name, @Nonnull FoxAttribute<?>... attributes) {
        this(type, name, ImmutableList.of(), attributes);
    }

    @Nonnull
    @Override
    public String getType() {
        return this.type;
    }

    @Nonnull
    @Override
    public String getName() {
        return this.name;
    }

    @Nonnull
    @Override
    public List<FoxArchetype> getParentArchetypes() {
        return this.parents;
    }

    @Nonnull
    @Override
    public Set<FoxArchetype> getAllParentArchetypes() {
        if (this.allParents == null) {
            this.allParents = FoxArchetype.super.getAllParentArchetypes();
        }
        return this.allParents;
    }

    @Nonnull
    @Override
    public Set<FoxArchetype> getAllArchetypes() {
        if (this.allArchetypes == null) {
            this.allArchetypes = FoxArchetype.super.getAllArchetypes();
        }
        return this.allArchetypes;
    }

    @Override
    public AttributeContainer getDelegateAttrHolder() {
        return attributeContainer;
    }

    protected void writeDefaultName(ArchetypeDisplayNameAttribute archetypeDisplayNameAttribute) {
        ArchetypeDisplayNameAttrValue value = archetypeDisplayNameAttribute.getValueProvider().get();
        value.set(this.getName());
        this.setAttrValue(value);
    }
}
