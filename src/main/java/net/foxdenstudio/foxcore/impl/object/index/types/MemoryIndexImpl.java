package net.foxdenstudio.foxcore.impl.object.index.types;

import com.google.common.collect.ImmutableSet;
import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.index.FoxObjectIndex;
import net.foxdenstudio.foxcore.api.object.index.Namespace;
import net.foxdenstudio.foxcore.api.object.index.types.MemoryIndex;
import net.foxdenstudio.foxcore.api.object.reference.IndexReference;
import net.foxdenstudio.foxcore.api.path.components.FoxFullPath;
import net.foxdenstudio.foxcore.api.path.components.FoxIndexPath;
import net.foxdenstudio.foxcore.api.path.components.FoxNamespacePath;
import net.foxdenstudio.foxcore.api.path.components.FoxObjectPath;
import net.foxdenstudio.foxcore.api.path.factory.FoxFullPathFactory;
import net.foxdenstudio.foxcore.api.path.factory.FoxIndexPathFactory;
import net.foxdenstudio.foxcore.api.path.factory.FoxNamespacePathFactory;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MemoryIndexImpl implements MemoryIndex {

    private final FoxFullPathFactory fullPathFactory;
    private final FoxIndexPathFactory indexPathFactory;
    private final FoxNamespacePathFactory namespacePathFactory;
    private final FoxIndexPath indexPath;

    private final Map<FoxObjectPath, IndexReference> indexMap;

    @Inject
    private MemoryIndexImpl(FoxFullPathFactory fullPathFactory,
                            FoxIndexPathFactory indexPathFactory,
                            FoxNamespacePathFactory namespacePathFactory) {
        this.fullPathFactory = fullPathFactory;
        this.indexPathFactory = indexPathFactory;
        this.namespacePathFactory = namespacePathFactory;
        this.indexPath = indexPathFactory.getPath(this.getIndexName(), namespacePathFactory.getEmptyPath());
        this.indexMap = new HashMap<>();
    }

    @Override
    public Optional<Namespace> getNamespace(FoxNamespacePath indexPath) {
        if (indexPath.isEmpty()) return Optional.of(this);
        return Optional.empty();
    }

    @Override
    public Namespace getDefaultNamespace() {
        return this;
    }

    @Override
    public boolean setDefaultNamespace(FoxNamespacePath indexPath) {
        return false;
    }

    @Override
    public String getIndexName() {
        return "mem";
    }

    @Override
    public Optional<FoxObject> getObject(FoxObjectPath path) {
        return Optional.ofNullable(this.indexMap.get(path)).flatMap(IndexReference::getObject);
    }

    @Override
    public Collection<FoxObjectPath> getAllObjectPaths() {
        return ImmutableSet.copyOf(this.indexMap.keySet());
    }

    @Override
    public FoxIndexPath getIndexPath() {
        return this.indexPath;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public Optional<IndexReference> addObject(FoxObject foxObject, FoxObjectPath path) {
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
        FoxObjectPath path;
        boolean valid;

        IndexReferenceImpl(FoxObject object, FoxObjectPath path) {
            this.object = object;
            this.path = path;
            this.valid = true;
        }

        @Override
        public Optional<FoxObject> getObject() {
            return Optional.ofNullable(object);
        }

        @Override
        public Optional<FoxFullPath> getPrimaryPath() {
            return Optional.ofNullable(path).map(path -> fullPathFactory.getPath(MemoryIndexImpl.this.indexPath, path));
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
