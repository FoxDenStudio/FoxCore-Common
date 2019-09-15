package net.foxdenstudio.foxcore.api.object.index;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.path.components.FoxIndexPath;
import net.foxdenstudio.foxcore.api.path.components.FoxObjectPath;

import java.util.Map;
import java.util.Optional;

public interface FoxMainIndex extends Namespace {

    Optional<FoxObjectIndex> getObjectIndex(String type);

    Map<String, FoxObjectIndex> getIndices();

    FoxObjectIndex getDefaultObjectIndex();

    @Override
    default Optional<FoxObject> getObject(FoxObjectPath path) {
        return this.getDefaultObjectIndex().getObject(path);
    }

    /**
     * Returns the default path of the main index.
     * Equivalent to getting the path of the default object index.
     *
     * @return default index path
     */
    @Override
    default FoxIndexPath getIndexPath() {
        return this.getDefaultObjectIndex().getIndexPath();
    }
}
