package net.foxdenstudio.foxcore.api.object.link.node;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.reference.types.LinkReference;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public interface LinkNodeContainerProxy extends LinkNodeContainer {

    @Override
    default boolean isValid() {
        return this.getDelegateSlotContainer().isValid();
    }

    @Override
    default void invalidate() {
        this.getDelegateSlotContainer().invalidate();
    }

    @Override
    default Optional<LinkReference> linkObject(FoxObject object) {
        return this.getDelegateSlotContainer().linkObject(object);
    }

    @Override
    default Optional<LinkReference> linkObject(@Nonnull FoxObject object, @Nullable StandardPathComponent path) {
        return this.getDelegateSlotContainer().linkObject(object, path);
    }

    @Override
    default FoxObject getContainerObject() {
        return this.getDelegateSlotContainer().getContainerObject();
    }

    @Override
    default Map<StandardPathComponent, LinkNode> getKnownNodes() {
        return this.getDelegateSlotContainer().getKnownNodes();
    }

    @Override
    default Optional<LinkNode> getNode(StandardPathComponent path, boolean create) {
        return this.getDelegateSlotContainer().getNode(path, create);
    }

    @Override
    default Optional<LinkNode> findNode(StandardPathComponent path, boolean create) {
        return this.getDelegateSlotContainer().findNode(path, create);
    }

    @Override
    default boolean addNode(LinkNode node, StandardPathComponent path) {
        return this.getDelegateSlotContainer().addNode(node, path);
    }

    @Override
    default Optional<LinkNode> removeNode(StandardPathComponent path) {
        return this.getDelegateSlotContainer().removeNode(path);
    }

    LinkNodeContainer getDelegateSlotContainer();
}
