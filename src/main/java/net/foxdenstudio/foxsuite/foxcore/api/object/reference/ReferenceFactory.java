package net.foxdenstudio.foxsuite.foxcore.api.object.reference;

import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxsuite.foxcore.api.object.link.node.LinkSlot;
import net.foxdenstudio.foxsuite.foxcore.api.object.reference.types.ExternalLinkReference;
import net.foxdenstudio.foxsuite.foxcore.api.object.reference.types.InternalLinkReference;

public interface ReferenceFactory {

    InternalLinkReference createInternalLinkReference(LinkSlot slot, FoxObject object);

    ExternalLinkReference createExternalLinkReference();

}
