package net.foxdenstudio.foxcore.api.object.link;

import net.foxdenstudio.foxcore.api.object.link.slot.LinkSlot;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import java.util.Map;
import java.util.Optional;

public interface LinksContainer {

    Map<StandardPathComponent, LinkSlot> getLinkSlots();

    Optional<LinkSlot> getDefaultLinkSlot();

    Optional<LinkSlot> getLinkSlot(StandardPathComponent path);

    default boolean addLinkSlot(StandardPathComponent path, LinkSlot slot) {
        return addLinkSlot(path, slot, false);
    }

    default boolean addLinkSlot(StandardPathComponent path, LinkSlot slot, boolean setAsDefault) {
        return addLinkSlot(path, slot, setAsDefault, false);
    }

    boolean addLinkSlot(StandardPathComponent path, LinkSlot slot, boolean setAsDefault, boolean overrideExistingDefault);
}
