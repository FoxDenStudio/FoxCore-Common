package net.foxdenstudio.foxcore.api.object.link;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.link.node.LinkNode;
import net.foxdenstudio.foxcore.api.object.link.node.LinkNodeContainer;
import net.foxdenstudio.foxcore.api.object.reference.types.LinkReference;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public interface LinkContainer extends LinkNodeContainer {

    Optional<LinkNode> getDefaultLinkNode();

    @Override
    boolean isValid();

    @Override
    void invalidate();

    @Override
    Optional<LinkReference> linkObject(@Nonnull FoxObject object, @Nullable StandardPathComponent path);

    @Override
    FoxObject getContainerObject();

    @Override
    Map<StandardPathComponent, LinkNode> getKnownNodes();

    @Override
    Optional<LinkNode> getNode(@Nonnull StandardPathComponent path, boolean create);

    @Override
    Optional<LinkNode> findNode(@Nonnull StandardPathComponent path, boolean create);

    default boolean addNode(@Nonnull LinkNode node, @Nullable StandardPathComponent path) {
        return this.addNode(node, path, false);
    }

    default boolean addNode(LinkNode node, StandardPathComponent path, boolean setAsDefault) {
        return this.addNode(node, path, setAsDefault, false);
    }

    boolean addNode(LinkNode node, StandardPathComponent path, boolean setAsDefault, boolean overrideExistingDefault);

    @Override
    Optional<LinkNode> removeNode(@Nonnull StandardPathComponent path);
}
