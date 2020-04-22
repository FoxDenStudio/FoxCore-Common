package net.foxdenstudio.foxcore.api.object.link.node;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.reference.types.LinkReference;

import java.util.Optional;

public interface LinkSlot extends LinkNode {

    default boolean hasLink() {
        return getLinkedObject().isPresent();
    }

    /**
     * Whether this slot is transient. Transient slots are empty, dynamic slots that exist to be used,
     * but aren't returned in collection based queries. They usually held only weakly.
     *
     * @return
     */
    @Override
    default boolean isTransient() {
        return this.isDynamic() && !this.hasLink();
    }

    Optional<LinkReference> getLinkedObject();

    @Override
    default Optional<LinkReference> linkObject(FoxObject object) {
        return linkObject(object, null);
    }

}
