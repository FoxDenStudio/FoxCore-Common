package net.foxdenstudio.foxcore.api.object.reference;

import net.foxdenstudio.foxcore.api.object.link.slot.LinkSlot;
import net.foxdenstudio.foxcore.api.object.link.slot.LinkSlotSchema;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import java.util.Optional;

public interface LinkReference {

    Optional<LinkSlot> getLinkSlot();

    Optional<LinkSlotSchema> getLinkSlotSchema();

    StandardPathComponent slotPath();

    /**
     * Whether this is a reference to an embedded object.
     * <p>
     * Embedded objects are special "nameless" objects that can only be referenced as a linked object of another object.
     * Although technically these objects exist in storage and memory just like any other, their primary paths contain a link section.
     * If an object is embedded as a link, unlink calls fail and instead the object must be moved or removed.
     *
     * @return whether the reference points to an embedded object.
     */
    boolean isEmbedded();

    /**
     * Unlinks the object from the slot it was linked to.
     * Fails if the object is no longer linked.
     * <p>
     * Also fails if link is embedded, as those need to be moved or deleted to be unlinked.
     *
     * @return whether the operation was successful.
     */
    boolean unlink();
}
