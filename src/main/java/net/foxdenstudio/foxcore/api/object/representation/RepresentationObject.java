package net.foxdenstudio.foxcore.api.object.representation;

import net.foxdenstudio.foxcore.api.object.FoxObject;

public interface RepresentationObject<R extends FoxRepresentable> extends FoxObject{

    R getRepresentedObject();
}
