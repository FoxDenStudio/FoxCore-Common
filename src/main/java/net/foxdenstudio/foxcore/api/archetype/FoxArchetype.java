package net.foxdenstudio.foxcore.api.archetype;

import com.google.common.collect.ImmutableSet;
import net.foxdenstudio.foxcore.api.archetype.container.ArchetypeContainer;
import net.foxdenstudio.foxcore.api.attribute.holder.AttributeHolder;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Set;

public interface FoxArchetype extends AttributeHolder {

    @Nonnull
    String getType();

    @Nonnull
    String getName();

    @Nonnull
    List<FoxArchetype> getParentArchetypes();

    @Nonnull
    default Set<FoxArchetype> getAllParentArchetypes() {
        ImmutableSet.Builder<FoxArchetype> builder = ImmutableSet.builder();
        List<FoxArchetype> parents = this.getParentArchetypes();
        builder.addAll(parents);
        for (FoxArchetype parent : parents) {
            builder.addAll(parent.getAllParentArchetypes());
        }
        return builder.build();
    }

    @Nonnull
    default Set<FoxArchetype> getAllArchetypes() {
        ImmutableSet.Builder<FoxArchetype> builder = ImmutableSet.builder();
        builder.addAll(this.getAllParentArchetypes());
        builder.add(this);
        return builder.build();
    }
}
