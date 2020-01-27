package net.foxdenstudio.foxcore.impl.object.index.types;

import com.google.common.collect.ImmutableSet;
import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxcore.api.object.index.Namespace;
import net.foxdenstudio.foxcore.api.object.index.types.MemoryIndex;
import net.foxdenstudio.foxcore.api.object.reference.IndexReference;
import net.foxdenstudio.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxcore.api.path.component.IndexPathComponent;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryIndexImpl implements MemoryIndex {

    private final FoxPathFactory pathFactory;
    private final IndexPathComponent indexPath;

    private final Map<StandardPathComponent, IndexReference> indexMap;

    @Inject
    private MemoryIndexImpl(FoxPathFactory pathFactory) {
        this.pathFactory = pathFactory;
        this.indexPath = new IndexPathComponent(this.getIndexName(), null);
        this.indexMap = new HashMap<>();
    }

    @Override
    public Optional<Namespace> getNamespace(StandardPathComponent namespacePath) {
        if (namespacePath == null) return Optional.of(this);
        return Optional.empty();
    }

    @Override
    public Namespace getDefaultNamespace() {
        return this;
    }

    @Override
    public boolean setDefaultNamespace(StandardPathComponent namespacePath) {
        return false;
    }

    @Override
    public String getIndexName() {
        return "mem";
    }

    @Override
    public Optional<FoxObject> getObject(StandardPathComponent path) {
        return Optional.ofNullable(this.indexMap.get(path)).flatMap(IndexReference::getObject);
    }

    @Override
    public Collection<StandardPathComponent> getAllObjectPaths() {
        return ImmutableSet.copyOf(this.indexMap.keySet());
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public Optional<IndexReference> addObject(FoxObject foxObject, StandardPathComponent path) {
        if (!this.indexMap.containsKey(path)) {
            IndexReferenceImpl ref = new IndexReferenceImpl(foxObject, path);
            foxObject.setIndexReference(ref);
            this.indexMap.put(path, ref);
            return Optional.of(ref);
        }
        return Optional.empty();
    }

    private class IndexReferenceImpl implements IndexReference {

        FoxObject object;
        StandardPathComponent path;
        boolean valid;

        IndexReferenceImpl(FoxObject object, StandardPathComponent path) {
            this.object = object;
            this.path = path;
            this.valid = true;
        }

        @Override
        public Optional<FoxObject> getObject() {
            return Optional.ofNullable(object);
        }

        @Override
        public Optional<FoxPath> getPrimaryPath() {
            return Optional.ofNullable(path).map(path -> pathFactory.from(MemoryIndexImpl.this.indexPath, path));
        }

        @Override
        public FoxObjectIndex getIndex() {
            return MemoryIndexImpl.this;
        }

        @Override
        public boolean stillValid() {
            return valid;
        }

        @Override
        public boolean removeObjectFromIndex() {
            if (valid) {
                MemoryIndexImpl.this.indexMap.remove(this.path);
                this.object = null;
                this.path = null;
                this.valid = false;
                return true;
            }
            return false;
        }
    }
}
