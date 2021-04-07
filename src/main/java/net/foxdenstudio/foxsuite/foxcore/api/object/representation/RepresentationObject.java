package net.foxdenstudio.foxsuite.foxcore.api.object.representation;

import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;

public interface RepresentationObject<R extends FoxRepresentable> extends FoxObject{

    R getRepresentedObject();
}
