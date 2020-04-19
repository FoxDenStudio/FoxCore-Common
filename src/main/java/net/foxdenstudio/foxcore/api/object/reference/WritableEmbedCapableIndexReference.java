package net.foxdenstudio.foxcore.api.object.reference;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.link.slot.LinkSlot;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import java.util.Optional;

public interface WritableEmbedCapableIndexReference extends WritableIndexReference, EmbedCapableIndexReference {

    Optional<EmbeddedIndexReference> embedObject(FoxObject object, StandardPathComponent path, boolean linked);

    Optional<EmbeddedLinkReference> embedObject(FoxObject object, LinkSlot linkSlot);

}
