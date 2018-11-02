package net.foxdenstudio.foxcore.api.object.index;

import java.util.Map;
import java.util.Optional;

public interface FoxMainIndex extends Namespace {

    Optional<FoxObjectIndex> getObjectIndex(String type);

    Map<String, FoxObjectIndex> getIndicies();

    FoxObjectIndex getDefaultObjectIndex();

}
