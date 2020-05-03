package net.foxdenstudio.foxcore.api.object.reference;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.link.node.LinkSlot;
import net.foxdenstudio.foxcore.api.object.reference.types.ExternalLinkReference;
import net.foxdenstudio.foxcore.api.object.reference.types.InternalLinkReference;

public interface ReferenceFactory {

    InternalLinkReference createInternalLinkReference(LinkSlot slot, FoxObject object);

    ExternalLinkReference createExternalLinkReference();

}
