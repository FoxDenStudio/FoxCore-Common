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

    default Optional<LinkReference> linkObject(@Nonnull FoxObject object) {
        return this.linkObject(object, null);
    }

    Optional<LinkReference> linkObject(@Nonnull FoxObject object, @Nullable StandardPathComponent path);

    FoxObject getContainerObject();

    Map<StandardPathComponent, LinkNode> getKnownNodes();

    default Optional<LinkNode> getNode(@Nonnull StandardPathComponent path) {
        return this.getNode(path, false);
    }

    /**
     * Gets a specific link slot that is a child of this link slot container.
     *
     * @param path The local/relative path of the link slot relative to its parent
     * @return The link slot, wrapped as an optional.
     */
    Optional<LinkNode> getNode(@Nonnull StandardPathComponent path, boolean create);

    default Optional<LinkNode> findNode(@Nonnull StandardPathComponent path) {
        return this.findNode(path, false);
    }

    /**
     * Recursively looks for a link slot at a particular path.
     * Returns a slot if one exists after traversing slots.
     *
     * @param path   the full (link) path to the slot.
     * @param create whether to create nodes to resolve this call. Does not guarantee success.
     * @return the found/created path, if available. Empty optional if not.
     */
    Optional<LinkNode> findNode(@Nonnull StandardPathComponent path, boolean create);

    default Optional<LinkNode> findFirst(@Nonnull StandardPathComponent path) {
        return this.findFirst(path, false);
    }

    /**
     * Returns the slot whose path is a prefix of the given path.
     * Effectively returns the first slot required to resolve a full path.
     *
     * This works because local slot paths must satisfy the no-prefix rule,
     * where no slot's path can be the prefix of another slot's path.
     *
     * @param path the path used to find the slot.
     * @param create whether to generate a slot for this call. Generally false.
     * @return A matching slot, if found. Returns an empty optional if not.
     */

    Optional<LinkNode> findFirst(@Nonnull StandardPathComponent path, boolean create);

    boolean addNode(LinkNode node, @Nullable StandardPathComponent path);

    Optional<LinkNode> removeNode(@Nonnull StandardPathComponent path);

    default boolean acceptsObject(@Nonnull FoxObject object) {
        return this.acceptsObject(object, null);
    }

    boolean acceptsObject(@Nonnull FoxObject object, @Nullable StandardPathComponent path);
}
