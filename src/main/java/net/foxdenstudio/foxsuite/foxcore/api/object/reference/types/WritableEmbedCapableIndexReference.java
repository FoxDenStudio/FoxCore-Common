package net.foxdenstudio.foxsuite.foxcore.api.object.reference.types;

import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxsuite.foxcore.api.object.link.node.LinkSlot;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;

import java.util.Optional;

public interface WritableEmbedCapableIndexReference extends WritableIndexReference, EmbedCapableIndexReference {

    Optional<EmbeddedIndexReference> embedObject(FoxObject object, StandardPathComponent path, boolean linked);

    Optional<EmbeddedLinkReference> embedObject(FoxObject object, LinkSlot linkSlot);

}
