package net.foxdenstudio.foxcore.api.object.link.node;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.reference.types.LinkReference;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public interface LinkNodeContainer {

    /**
     * Returns whether this container still contains meaningful information.
     * This equates effectively to the "live-ness" of the container.
     *
     * @return whether this instance is still in use.
     */
    boolean isValid();

    /**
     * Call to notify this container that it may no longer be valid.
     * The container should try to verify its invalidation by checking if the associated FoxObject is still live.
     */
    void invalidate();

    default Optional<LinkReference> linkObject(FoxObject object) {
        return this.linkObject(object, null);
    }

    Optional<LinkReference> linkObject(@Nonnull FoxObject object, @Nullable StandardPathComponent path);

    FoxObject getContainerObject();

    Map<StandardPathComponent, LinkNode> getKnownNodes();

    default Optional<LinkNode> getNode(StandardPathComponent path) {
        return this.getNode(path, false);
    }

    /**
     * Gets a specific link slot that is a child of this link slot container.
     *
     * @param path The local/relative path of the link slot relative to its parent
     * @return The link slot, wrapped as an optional.
     */
    Optional<LinkNode> getNode(StandardPathComponent path, boolean create);

    default Optional<LinkNode> findNode(StandardPathComponent path) {
        return this.findNode(path, false);
    }

    /**
     * Recursively looks for a link slot at a particular path.
     * Returns a slot if one exists after traversing slots.
     *
     * @param path   the full (link) path to the slot.
     * @param create
     * @return
     */
    Optional<LinkNode> findNode(StandardPathComponent path, boolean create);

    boolean addNode(LinkNode node, StandardPathComponent path);

    Optional<LinkNode> removeNode(StandardPathComponent path);
}
