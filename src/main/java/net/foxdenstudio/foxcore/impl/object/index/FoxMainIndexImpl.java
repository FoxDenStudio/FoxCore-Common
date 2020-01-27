package net.foxdenstudio.foxcore.impl.object.index;

import com.google.common.collect.ImmutableMap;
import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxcore.api.object.index.WritableIndex;
import net.foxdenstudio.foxcore.api.object.index.types.MemoryIndex;
import net.foxdenstudio.foxcore.api.object.reference.IndexReference;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Singleton
public class FoxMainIndexImpl implements FoxMainIndex {

    private final MemoryIndex memoryIndex;
    private final Map<String, FoxObjectIndex> indexMap;

    @Inject
    private FoxMainIndexImpl(MemoryIndex memoryIndex) {
        this.memoryIndex = memoryIndex;
        this.indexMap = ImmutableMap.of("mem", memoryIndex);
    }

    @Override
    public Optional<FoxObjectIndex> getObjectIndex(String type) {
        return Optional.ofNullable(this.indexMap.get(type));
    }

    @Override
    public Map<String, FoxObjectIndex> getIndices() {
        return this.indexMap;
    }

    @Nonnull
    @Override
    public WritableIndex getDefaultObjectIndex() {
        return this.memoryIndex;
    }

    @Override
    public Optional<IndexReference> addObject(FoxObject foxObject, StandardPathComponent path) {
        return this.getDefaultObjectIndex().addObject(foxObject, path);
    }

    @Override
    public Collection<StandardPathComponent> getAllObjectPaths() {
        return this.memoryIndex.getAllObjectPaths();
    }
}
