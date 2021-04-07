package net.foxdenstudio.foxsuite.foxcore.api.object.representation;

import javax.annotation.Nullable;
import java.util.Optional;

public interface FoxRepresentable {

    Optional<? extends RepresentationObject<?>> getRepresentation();

    boolean setRepresentation(@Nullable RepresentationObject<?> representation);
}
