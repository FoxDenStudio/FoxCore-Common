package net.foxdenstudio.foxsuite.foxcore.api.object.reference.types;

import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.Namespace;
import net.foxdenstudio.foxsuite.foxcore.api.object.reference.FoxObjectReference;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPath;

import java.util.Optional;

public interface IndexReference extends FoxObjectReference {

    @Override
    Optional<FoxObject> getObject();

    Optional<FoxPath> getPrimePath();

    FoxObjectIndex getIndex();

    Namespace getNamespace();

    boolean isEmbedded();

}
