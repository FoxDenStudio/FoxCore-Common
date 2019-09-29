package net.foxdenstudio.foxcore.api.object.representation;

import net.foxdenstudio.foxcore.api.object.FoxObject;

import java.util.Optional;

public interface FoxRepresentable {

    Optional<? extends RepresentationObject<?>> getRepresentation();
}
