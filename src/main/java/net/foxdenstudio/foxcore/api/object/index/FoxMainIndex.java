package net.foxdenstudio.foxcore.api.object.index;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.reference.IndexReference;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface FoxMainIndex extends WritableNamespace {

    Optional<FoxObjectIndex> getObjectIndex(String type);

    Map<String, FoxObjectIndex> getIndices();

    @Nonnull
    WritableIndex getDefaultObjectIndex();

    @Override
    default Optional<FoxObject> getObject(StandardPathComponent path) {
        return this.getDefaultObjectIndex().getObject(path);
    }

    @Override
    Optional<IndexReference> addObject(FoxObject foxObject, StandardPathComponent path);

    @Override
    Collection<StandardPathComponent> getAllObjectPaths();
}
