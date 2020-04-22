package net.foxdenstudio.foxcore.api.object.link.node;

import net.foxdenstudio.foxcore.api.object.link.LinkSchema;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

public interface LinkNode extends LinkNodeContainer {

    StandardPathComponent nodePath();

    StandardPathComponent localNodePath();

    LinkSchema getSchema();

    /**
     * Whether this slot is dynamically generated. Generally true for slots that are elements of maps or lists.
     *
     * @return whether this slot is dynamically generated.
     */
    boolean isDynamic();

    boolean isTransient();

    LinkNodeContainer getParentContainer();

    /**
     * Whether or not the sub-slot schema is restricted or not.
     * If false, sub-slots cannot be added or removed from the outside.
     *
     * @return can add/remove sub-slots
     */
    boolean freeStructure();

    /**
     * Whether this link slot supports object embedding.
     * <p>
     * If this slot does not support embedding, then all sub-slots also cannot support it.
     *
     * @return whether this slot supports embedded objects.
     */
    boolean supportsEmbedding();
}
