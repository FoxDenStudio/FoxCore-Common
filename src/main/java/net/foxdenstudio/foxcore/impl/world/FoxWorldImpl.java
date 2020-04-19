package net.foxdenstudio.foxcore.impl.world;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.foxdenstudio.foxcore.api.archetype.ArchetypeBase;
import net.foxdenstudio.foxcore.api.object.representation.RepresentationBase;
import net.foxdenstudio.foxcore.api.object.representation.RepresentationObject;
import net.foxdenstudio.foxcore.api.world.FoxWorld;
import net.foxdenstudio.foxcore.content.archetype.RepresentationArchetype;
import net.foxdenstudio.foxcore.content.attribute.ArchetypeDisplayNameAttribute;
import net.foxdenstudio.foxcore.platform.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

public class FoxWorldImpl implements FoxWorld {

    private String name;
    private UUID uuid;
    private Path directory;

    private transient World world;
    private transient RepresentationObject<?> representationObject;

    @Override
    public Optional<String> getName() {
        if (this.name == null && this.world != null) {
            this.name = this.world.getName();
        }
        return Optional.ofNullable(name);
    }

    @Override
    public Optional<Path> getDirectory() {
        return Optional.ofNullable(this.directory);
    }

    @Override
    public Optional<World> getOnlineWorld() {
        return Optional.ofNullable(this.world);
    }

    @Override
    public Optional<? extends RepresentationObject<?>> getRepresentation() {
        return Optional.ofNullable(representationObject);
    }

    @Override
    public boolean setRepresentation(@Nullable RepresentationObject<?> representation) {
        if (representation == null) {
            if (this.representationObject != null) {
                this.representationObject = null;
                return true;
            } else {
                return false;
            }
        } else if (representation.getRepresentedObject() == this) {
            this.representationObject = representation;
            return true;
        } else return false;
    }

    @Override
    public UUID getUniqueID() {
        return this.uuid;
    }

    public static class Object extends RepresentationBase<FoxWorldImpl> {

        public Object(FoxWorldImpl represented, Type archetype) {
            super(represented, archetype);
        }
    }

    @Singleton
    public static class Type extends ArchetypeBase {

        @Inject
        private Type(@Nonnull RepresentationArchetype representationArchetype, @Nonnull ArchetypeDisplayNameAttribute archetypeDisplayNameAttribute) {
            super("world", "World", representationArchetype, archetypeDisplayNameAttribute);
            this.writeDefaultName(archetypeDisplayNameAttribute);
        }
    }

}
