package net.foxdenstudio.foxcore.api.object.link.node;

import net.foxdenstudio.foxcore.api.object.reference.FoxObjectReference;
import net.foxdenstudio.foxcore.api.object.reference.types.LinkReference;

import java.util.Optional;

public interface LinkSlot extends LinkNode {

    default boolean hasLink() {
        return getLinkedObject().isPresent();
    }

    /**
     * Whether this slot is transient. Transient slots are empty, dynamic slots that exist to be used,
     * but aren't returned in collection based queries. They are usually held only weakly.
     *
     * @return whether this slot is transient.
     */
    @Override
    default boolean isTransient() {
        return this.isDynamic() && !this.hasLink();
    }

    /**
     * Whether this slot is dynamically generated. Generally true for slots that are elements of maps or lists.
     *
     * @return whether this slot is dynamically generated.
     */
    @Override
    boolean isDynamic();

    Optional<LinkReference> getLinkedObject();

    /**
     * Attempts to remove a bound object, if it exists.
     *
     * @return the removed object's reference, if possible.
     */

    Optional<FoxObjectReference> removeObject();

    /**
     * Whether this link slot supports object embedding.
     * <p>
     * If this slot does not support embedding, then all child nodes also cannot support it.
     *
     * @return whether this slot supports embedded objects.
     */
    @Override
    boolean supportsEmbedding();
}
