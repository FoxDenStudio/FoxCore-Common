package net.foxdenstudio.foxcore.api.archetype;

import com.google.common.collect.ImmutableSet;
import net.foxdenstudio.foxcore.api.attribute.holder.AttributeHolder;
import net.foxdenstudio.foxcore.api.object.link.schema.LinkSchema;
import net.foxdenstudio.foxcore.api.object.representation.FoxRepresentable;
import net.foxdenstudio.foxcore.api.object.representation.RepresentationObject;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FoxArchetype extends AttributeHolder, FoxRepresentable {

    @Nonnull
    String getType();

    @Nonnull
    String getName();

    @Nonnull
    List<FoxArchetype> getParentArchetypes();

    @Nonnull
    Optional<LinkSchema> getLinkSchema();

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

    @Override
    Optional<? extends RepresentationObject<?>> getRepresentation();

    @Override
    boolean setRepresentation(@Nullable RepresentationObject<?> representation);
}
