package net.foxdenstudio.foxcore.api.object.index;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.path.components.FoxNamespacePath;
import net.foxdenstudio.foxcore.api.path.components.FoxObjectPath;

import java.util.Map;
import java.util.Optional;

public interface FoxMainIndex extends Namespace {

    Optional<FoxObjectIndex> getObjectIndex(String type);

    Map<String, FoxObjectIndex> getIndicies();

    FoxObjectIndex getDefaultObjectIndex();

    @Override
    default Optional<FoxObject> getObject(FoxObjectPath path) {
        return this.getDefaultObjectIndex().getObject(path);
    }

    @Override
    default FoxNamespacePath getNamespacePath() {
        return null;
    }


}
