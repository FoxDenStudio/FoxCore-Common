package net.foxdenstudio.foxsuite.foxcore.impl.object.index;

import com.google.common.collect.ImmutableMap;
import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.FoxMainIndex;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.WritableIndex;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.types.FileIndex;
import net.foxdenstudio.foxsuite.foxcore.api.object.index.types.MemoryIndex;
import net.foxdenstudio.foxsuite.foxcore.api.object.reference.types.IndexReference;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxsuite.foxcore.api.path.section.ObjectPathSection;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class FoxMainIndexImpl implements FoxMainIndex {

    private final MemoryIndex memoryIndex;
    private final FileIndex fileIndex;
    private final Map<String, FoxObjectIndex> indexMap;

    private transient Map<String, FoxObjectIndex> indexMapCopy = null;

    @Inject
    private FoxMainIndexImpl(MemoryIndex memoryIndex, FileIndex fileIndex) {
        this.memoryIndex = memoryIndex;
        this.fileIndex = fileIndex;
        this.indexMap = new HashMap<>();
        this.indexMap.put(memoryIndex.getIndexName(), memoryIndex);
        this.indexMap.put(fileIndex.getIndexName(), fileIndex);
    }

    @Override
    public Optional<FoxObjectIndex> getObjectIndex(String name) {
        return Optional.ofNullable(this.indexMap.get(name));
    }

    @Override
    public Map<String, FoxObjectIndex> getIndices() {
        if (this.indexMapCopy == null) this.indexMapCopy = ImmutableMap.copyOf(this.indexMap);
        return this.indexMapCopy;
    }

    @Nonnull
    @Override
    public WritableIndex getDefaultObjectIndex() {
        return this.memoryIndex;
    }

    @Nonnull
    @Override
    public MemoryIndex getMemoryIndex() {
        return this.memoryIndex;
    }

    @Override
    public Optional<IndexReference> addObject(FoxObject foxObject, ObjectPathSection path) {
        return this.getDefaultObjectIndex().addObject(foxObject, path);
    }

    @Override
    public Collection<StandardPathComponent> getAllObjectPaths() {
        return this.memoryIndex.getAllObjectPaths();
    }

    @Override
    public boolean contains(FoxObject foxObject) {
        for (FoxObjectIndex index : this.indexMap.values()) {
            if (index.contains(foxObject)) return true;
        }
        return false;
    }
}
