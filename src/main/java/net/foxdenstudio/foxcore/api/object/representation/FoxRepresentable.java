package net.foxdenstudio.foxcore.api.object.representation;

import java.util.Optional;

public interface FoxRepresentable<T extends RepresentationObject<T, R>, R extends FoxRepresentable<T, R>> {

    Optional<T> getRepresentation();
}
