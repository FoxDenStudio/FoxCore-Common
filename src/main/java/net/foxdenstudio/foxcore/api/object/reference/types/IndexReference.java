package net.foxdenstudio.foxcore.api.object.reference.types;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxcore.api.object.index.Namespace;
import net.foxdenstudio.foxcore.api.object.reference.FoxObjectReference;
import net.foxdenstudio.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import java.util.Optional;

public interface IndexReference extends FoxObjectReference {

    @Override
    Optional<FoxObject> getObject();

    Optional<FoxPath> getPrimePath();

    FoxObjectIndex getIndex();

    Namespace getNamespace();

    boolean isEmbedded();

}
