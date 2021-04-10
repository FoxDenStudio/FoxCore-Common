package net.foxdenstudio.foxsuite.foxcore.api.object.link.node;

import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxsuite.foxcore.api.object.reference.types.LinkReference;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;

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

    /**
     * Links object with empty path target.
     * See {@link #linkObject(FoxObject, StandardPathComponent)} for additional documentation.
     *
     * @param object the object to link
     * @return an optional link reference, present if the link is successful, empty if it isn't
     */
    default Optional<LinkReference> linkObject(@Nonnull FoxObject object) {
        return this.linkObject(object, StandardPathComponent.empty());
    }

    /**
     * Links an object, targeting this link node container.
     * At this level of abstraction, the default behavior is to resolve a non-empty path and defer the operation,
     * since link node containers (and link nodes) can't hold an actual link.
     * However a useful mechanic of defining a nullable path is the ability to add default routing and sorting behavior.
     *
     * @param object the object to link
     * @param path an optional path target to resolve against this link node container
     * @return an optional link reference, present if the link is successful and empty if it is not.
     */
    Optional<LinkReference> linkObject(@Nonnull FoxObject object, @Nullable StandardPathComponent path);

    /**
     * Get the main container fox-object that's holding this link node container.
     * @return the owning fox-object
     */
    Optional<FoxObject> getContainerObject();

    /**
     * Gets a immutable map copy that contains the direct children of this link node.
     * @return map of direct children
     */
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

    /**
     * Finds a node using a path to search. Does not create a node.
     * See {@link #findNode(StandardPathComponent, boolean)} for additional documentation.
     * @param path
     * @return
     */
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

    /**
     * Finds the first node resolvable using this path. This node will be a direct child.
     * See {@link #findFirst(StandardPathComponent, boolean)}
     * @param path the path used to search for a child node.
     * @return an optional link node, present if found, empty otherwise.
     */
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

    boolean addNode(@Nonnull LinkNode node, @Nullable StandardPathComponent path);

    Optional<LinkNode> removeNode(@Nonnull StandardPathComponent path);

    default boolean acceptsObject(@Nonnull FoxObject object) {
        return this.acceptsObject(object, null);
    }

    /**
     * Returns whether this container can accept an object link at a target path.
     * This call functions similarly to {@link #linkObject(FoxObject, StandardPathComponent)} except for the fact that
     * it doesn't actually perform the linking action, but merely answers like a whitelist.
     *
     * Since this is a method used for checking inputs prior to an actual link call,
     * it is expected that this method may be called many times as part of a filter against a collection of objects.
     * Because this method also resolves paths, it is recommended that looping code try to resolve to the correct node first,
     * so that the path argument can be empty.
     *
     * @param object the object in question
     * @param path the path target, ideally empty.
     * @return whether this container can accept an object at this target.
     */
    boolean acceptsObject(@Nonnull FoxObject object, @Nullable StandardPathComponent path);
}
