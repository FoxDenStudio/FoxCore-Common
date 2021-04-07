package net.foxdenstudio.foxsuite.foxcore.impl.world;


import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import net.foxdenstudio.foxsuite.foxcore.api.object.link.node.LinkSetNode;
import net.foxdenstudio.foxsuite.foxcore.api.object.representation.RepresentationBase;
import net.foxdenstudio.foxsuite.foxcore.api.object.representation.RepresentationObject;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxsuite.foxcore.api.world.FoxWorld;
import net.foxdenstudio.foxsuite.foxcore.platform.world.World;

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
        if (this.world == null && world.getName().equals(this.name)) {
            this.world = world;
            if (this.directory == null) this.directory = world.getDirectory();
        }
    }

    @Override
    public Optional<FoxWorld.Object> getRepresentation() {
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
    public UUID getUniqueId() {
        return this.world == null ? this.uuid : (this.uuid = world.getUniqueId());
    }

    public static class FoxObject extends RepresentationBase<FoxWorld, Type> implements FoxWorld.Object {

        public static final StandardPathComponent REGION_PATH = StandardPathComponent.of("r");

        @AssistedInject
        private FoxObject(@Assisted FoxWorldImpl represented, Type archetype) {
            super(represented, archetype);
            this.linkContainer.addNode(new LinkSetNode(this, null, this.linkContainer, REGION_PATH, 1, false, false), REGION_PATH, true, true);
        }
    }

    @Singleton
    public static final class RepObjectFactory {

        private final Type archetype;

        @Inject
        private RepObjectFactory(Type archetype) {
            this.archetype = archetype;
        }

        public FoxWorldImpl.FoxObject get(FoxWorldImpl represented) {
            return new FoxWorldImpl.FoxObject(represented, this.archetype);
        }
    }

}
