package net.foxdenstudio.foxcore.api.object.link.slot;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.reference.LinkReference;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public interface LinkSlot {

    StandardPathComponent slotPath();

    LinkSlotSchema getSchema();

    default boolean hasLink() {
        return getLinkedObject().isPresent();
    }

    /**
     * Whether this slot is dynamically generated. Generally true for slots that are elements of maps or lists.
     *
     * @return whether this slot is dynamically generated.
     */
    boolean isDynamic();

    /**
     * Whether this slot is transient. Transient slots are empty, dynamic slots that exist to be used,
     * but aren't returned in collection based queries. They usually held only weakly.
     *
     * @return
     */
    default boolean isTransient() {
        return this.isDynamic() && !this.hasLink();
    }

    boolean stillValid();

    Optional<LinkReference> getLinkedObject();

    default Optional<LinkReference> addObject(FoxObject object) {
        return addObject(object, null);
    }

    Optional<LinkReference> addObject(FoxObject object, @Nullable StandardPathComponent path);

    FoxObject getContainerObject();

    Optional<LinkSlot> getParentLinkSlot();

    Map<StandardPathComponent, LinkSlot> getKnownSubSlots();

    Optional<LinkSlot> getSubSlot(StandardPathComponent path);

    /**
     * Whether or not the sub-slot schema is restricted or not.
     * If false, sub-slots cannot be added or removed from the outside.
     *
     * @return can add/remove sub-slots
     */
    boolean freeStructure();

    boolean addSubSlot(LinkSlot slot, StandardPathComponent path);

    Optional<LinkSlot> removeSubSlot(StandardPathComponent path);

    /**
     * Whether this link slot supports object embedding.
     * <p>
     * If this slot does not support embedding, then all sub-slots also cannot support it.
     *
     * @return whether this slot supports embedded objects.
     */
    boolean supportsEmbedded();
}
