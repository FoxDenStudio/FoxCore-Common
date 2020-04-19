package net.foxdenstudio.foxcore.api.object.index;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.index.types.MemoryIndex;
import net.foxdenstudio.foxcore.api.object.reference.IndexReference;
import net.foxdenstudio.foxcore.api.path.section.IndexPathSection;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.api.path.section.LinkPathSection;
import net.foxdenstudio.foxcore.api.path.section.ObjectPathSection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface FoxMainIndex extends WritableNamespace {

    Optional<FoxObjectIndex> getObjectIndex(String name);

    Map<String, FoxObjectIndex> getIndices();

    @Nonnull
    WritableIndex getDefaultObjectIndex();

    @Nonnull
    MemoryIndex getMemoryIndex();

    @Override
    default IndexPathSection getIndexPath() {
        return this.getDefaultObjectIndex().getIndexPath();
    }

    @Override
    default Optional<IndexReference> getObjectReference(StandardPathComponent path, @Nullable LinkPathSection links) {
        return this.getDefaultObjectIndex().getObjectReference(path, links);
    }

    @Override
    Optional<IndexReference> addObject(FoxObject foxObject, ObjectPathSection path);

    @Override
    Collection<StandardPathComponent> getAllObjectPaths();
}
