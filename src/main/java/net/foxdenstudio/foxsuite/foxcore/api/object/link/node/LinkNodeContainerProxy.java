package net.foxdenstudio.foxsuite.foxcore.api.object.link.node;

import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxsuite.foxcore.api.object.reference.types.LinkReference;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public interface LinkNodeContainerProxy extends LinkNodeContainer {

    @Override
    default boolean isValid() {
        return this.getDelegateNodeContainer().isValid();
    }

    @Override
    default void invalidate() {
        this.getDelegateNodeContainer().invalidate();
    }

    @Override
    default Optional<LinkReference> linkObject(@Nonnull FoxObject object) {
        return this.getDelegateNodeContainer().linkObject(object);
    }

    @Override
    default Optional<LinkReference> linkObject(@Nonnull FoxObject object, @Nullable StandardPathComponent path) {
        return this.getDelegateNodeContainer().linkObject(object, path);
    }

    @Override
    default FoxObject getContainerObject() {
        return this.getDelegateNodeContainer().getContainerObject();
    }

    @Override
    default Map<StandardPathComponent, LinkNode> getKnownNodes() {
        return this.getDelegateNodeContainer().getKnownNodes();
    }

    @Override
    default Optional<LinkNode> getNode(@Nonnull StandardPathComponent path, boolean create) {
        return this.getDelegateNodeContainer().getNode(path, create);
    }

    @Override
    default Optional<LinkNode> findNode(@Nonnull StandardPathComponent path, boolean create) {
        return this.getDelegateNodeContainer().findNode(path, create);
    }

    @Override
    default boolean addNode(@Nonnull LinkNode node, @Nullable StandardPathComponent path) {
        return this.getDelegateNodeContainer().addNode(node, path);
    }

    @Override
    default Optional<LinkNode> removeNode(@Nonnull StandardPathComponent path) {
        return this.getDelegateNodeContainer().removeNode(path);
    }

    LinkNodeContainer getDelegateNodeContainer();
}
