package net.foxdenstudio.foxsuite.foxcore.api.world;

import net.foxdenstudio.foxsuite.foxcore.api.archetype.ArchetypeBase;
import net.foxdenstudio.foxsuite.foxcore.api.archetype.type.FoxType;
import net.foxdenstudio.foxsuite.foxcore.api.object.representation.FoxRepresentable;
import net.foxdenstudio.foxsuite.foxcore.api.object.representation.RepresentationObject;
import net.foxdenstudio.foxsuite.foxcore.api.util.FoxIdentifiable;
import net.foxdenstudio.foxsuite.foxcore.content.archetype.RepresentationArchetype;
import net.foxdenstudio.foxsuite.foxcore.content.attribute.ArchetypeDisplayNameAttribute;
import net.foxdenstudio.foxsuite.foxcore.platform.world.World;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.file.Path;
import java.util.Optional;

public interface FoxWorld extends FoxRepresentable, FoxIdentifiable {

    String getName();

    Optional<Path> getDirectory();

    Optional<World> getOnlineWorld();

    interface Object extends RepresentationObject<FoxWorld> {

    }

    @Singleton
    class Type extends ArchetypeBase implements FoxType {
        @Inject
        private Type(@Nonnull RepresentationArchetype representationArchetype, @Nonnull ArchetypeDisplayNameAttribute archetypeDisplayNameAttribute) {
            super("world", "World", representationArchetype, archetypeDisplayNameAttribute);
            this.writeDefaultName(archetypeDisplayNameAttribute);
        }
    }
}
