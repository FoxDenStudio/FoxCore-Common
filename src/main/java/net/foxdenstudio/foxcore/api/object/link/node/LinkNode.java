package net.foxdenstudio.foxcore.api.object.link.node;

import net.foxdenstudio.foxcore.api.object.link.schema.LinkSchema;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

public interface LinkNode extends LinkNodeContainer {

    StandardPathComponent nodePath();

    StandardPathComponent localNodePath();

    LinkSchema getSchema();

    /**
     * Whether this node is dynamically generated. Generally true for nodes that are elements of maps or lists.
     *
     * @return whether this node is dynamically generated.
     */
    boolean isDynamic();

    /**
     * Whether this node is transient.
     * Transient nodes are dynamic nodes that exist to be used, but aren't returned in collection based queries.
     * They may contain other transient nodes and slots, but cease to be transient
     * if one of their child nodes are no longer transient. While transient, nodes are usually held only weakly.
     *
     * @return whether this node is transient.
     */
    boolean isTransient();

    LinkNodeContainer getParentContainer();

    /**
     * Whether or not the node schema is restricted or not.
     * If true, child nodes cannot be added or removed externally, and changes are managed by this node.
     * This does not necessarily apply to children of children, as child nodes of this node may not be structured.
     *
     * @return whether child nodes can be added or removed from this node externally.
     */
    boolean structured();

    /**
     * Whether this link node supports object embedding.
     * <p>
     * If this node does not support embedding, then all child nodes also cannot support it.
     *
     * @return whether this slot supports embedded objects.
     */
    boolean supportsEmbedding();
}
