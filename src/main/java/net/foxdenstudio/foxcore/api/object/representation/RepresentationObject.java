package net.foxdenstudio.foxcore.api.object.representation;

import net.foxdenstudio.foxcore.api.object.FoxObject;

public interface RepresentationObject<T extends RepresentationObject<T, R>, R extends FoxRepresentable<T,R>> extends FoxObject<T> {

    R getRepresentedObject();
}
