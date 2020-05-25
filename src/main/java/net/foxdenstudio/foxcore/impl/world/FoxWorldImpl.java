package net.foxdenstudio.foxcore.impl.world;


import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import net.foxdenstudio.foxcore.api.archetype.ArchetypeBase;
import net.foxdenstudio.foxcore.api.object.representation.RepresentationBase;
import net.foxdenstudio.foxcore.api.object.representation.RepresentationObject;
import net.foxdenstudio.foxcore.api.world.FoxWorld;
import net.foxdenstudio.foxcore.content.archetype.RepresentationArchetype;
import net.foxdenstudio.foxcore.content.attribute.ArchetypeDisplayNameAttribute;
import net.foxdenstudio.foxcore.platform.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;

public class FoxWorldImpl implements FoxWorld {

    private String name;
    private UUID uuid;
    private Path directory;

    private transient World world;
    private transient FoxWorld.Object representationObject;

    public FoxWorldImpl(String name, UUID uuid, @Nullable Path directory) {
        this.name = name;
        this.uuid = uuid;
        this.directory = directory;
    }

    @Override
    public String getName() {
        if (this.name == null && this.world != null) {
            this.name = this.world.getName();
        }
        return name;
    }

    @Override
    public Optional<Path> getDirectory() {
        return Optional.ofNullable(this.directory);
    }

    public void setDirectory(Path directory) {
        this.directory = directory;
    }

    @Override
    public Optional<World> getOnlineWorld() {
        return Optional.ofNullable(this.world);
    }

    public void setOnlineWorld(World world) {
        this.world = world;
    }

    @Override
    public Optional<? extends FoxWorld.Object> getRepresentation() {
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
            this.representationObject = (Object) representation;
            return true;
        } else return false;
    }

    @Override
    public UUID getUniqueID() {
        return this.world == null ? this.uuid : (this.uuid = world.getUniqueID());
    }

    public static class FoxObject extends RepresentationBase<FoxWorld, FoxType> implements FoxWorld.Object {
        @AssistedInject
        private FoxObject(@Assisted FoxWorldImpl represented, FoxType archetype) {
            super(represented, archetype);
        }
    }

    @Singleton
    public static class FoxType extends ArchetypeBase {

        @Inject
        private FoxType(@Nonnull RepresentationArchetype representationArchetype, @Nonnull ArchetypeDisplayNameAttribute archetypeDisplayNameAttribute) {
            super("world", "World", representationArchetype, archetypeDisplayNameAttribute);
            this.writeDefaultName(archetypeDisplayNameAttribute);
        }
    }

    @Singleton
    public static final class RepObjectFactory{

        private final FoxType foxType;

        @Inject
        private RepObjectFactory(FoxType foxType) {
            this.foxType = foxType;
        }

        public FoxWorldImpl.FoxObject get(FoxWorldImpl represented){
            return new FoxWorldImpl.FoxObject(represented, this.foxType);
        }
    }

}
