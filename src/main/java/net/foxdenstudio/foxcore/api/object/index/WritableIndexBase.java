package net.foxdenstudio.foxcore.api.object.index;

import com.google.common.collect.ImmutableSet;
import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.reference.IndexReference;
import net.foxdenstudio.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxcore.api.path.section.IndexPathSection;
import net.foxdenstudio.foxcore.api.path.section.ObjectPathSection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class WritableIndexBase implements WritableIndex {

    protected final FoxPathFactory pathFactory;

    protected final IndexPathSection indexPath;

    protected final Map<StandardPathComponent, NamespaceBase> namespaces;
    protected NamespaceBase defaultNamespace;
    protected boolean namelessNamespace = false;

    protected final Map<StandardPathComponent, Map<StandardPathComponent, IndexReference>> indexMapMap;
    protected Map<StandardPathComponent, IndexReference> defaultIndexMap;


    protected WritableIndexBase(@Nonnull FoxPathFactory pathFactory) {
        this.pathFactory = pathFactory;
        this.indexPath = new IndexPathSection(this.getIndexName(), null);
        this.namespaces = new HashMap<>();
        this.indexMapMap = new HashMap<>();
        this.registerNamespaces();
        if (defaultNamespace == null) {
            this.defaultNamespace = new NamespaceBase();
            this.defaultIndexMap = this.defaultNamespace.indexMap;
            this.namelessNamespace = true;
        }
    }

    protected abstract void registerNamespaces();


    protected boolean registerNamespace(NamespaceBase namespace, @Nullable StandardPathComponent path) {
        if (path == null) {
            if(!this.namelessNamespace){
                this.namelessNamespace = true;
                this.defaultNamespace = namespace;
                this.defaultIndexMap = namespace.indexMap;
                return true;
            }
            return false;
        } else if (!this.namespaces.containsKey(path)) {
            if (!this.namelessNamespace && this.defaultNamespace == null) this.defaultNamespace = namespace;
            this.namespaces.put(path, namespace);
            this.indexMapMap.put(path, namespace.indexMap);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Namespace> getNamespace(StandardPathComponent namespacePath) {
        if (namespacePath == null) return Optional.of(this.defaultNamespace);
        return Optional.ofNullable(this.namespaces.get(namespacePath));
    }

    @Override
    public NamespaceBase getDefaultNamespace() {
        return defaultNamespace;
    }

    @Override
    public boolean setDefaultNamespace(StandardPathComponent namespacePath) {
        if (namelessNamespace) return false;
        NamespaceBase namespace = this.namespaces.get(namespacePath);
        if (namespace == null) return false;
        this.defaultNamespace = namespace;
        return true;
    }

    @Override
    public String getIndexName() {
        return "mem";
    }

    @Override
    public Optional<IndexReference> getObjectReference(StandardPathComponent path) {
        return this.defaultNamespace.getObjectReference(path);
    }

    @Override
    public Collection<StandardPathComponent> getAllObjectPaths() {
        return this.defaultNamespace.getAllObjectPaths();
    }

    @Override
    public IndexPathSection getIndexPath() {
        return this.indexPath;
    }

    @Override
    public Optional<IndexReference> addObject(FoxObject foxObject, ObjectPathSection path) {
        return this.defaultNamespace.addObject(foxObject, path);
    }

    protected IndexReference createReference(FoxObject foxObject, ObjectPathSection path, NamespaceBase namespace) {
        return new IndexReferenceBase(foxObject, path, namespace);
    }

    private boolean removeObject(NamespaceBase namespace, ObjectPathSection path) {
        return namespace.indexMap.remove(path.getPathComponent()) != null;
    }

    public class IndexReferenceBase implements IndexReference {

        protected FoxObject object;
        protected ObjectPathSection path;

        protected NamespaceBase namespace;

        protected boolean valid;

        protected IndexReferenceBase(FoxObject object, ObjectPathSection path, NamespaceBase namespace) {
            this.object = object;
            this.path = path;
            this.namespace = namespace;
            this.valid = true;
        }

        @Override
        public Optional<FoxObject> getObject() {
            return Optional.ofNullable(object);
        }

        @Override
        public Optional<FoxPath> getPrimaryPath() {
            return Optional.ofNullable(path).map(path -> pathFactory.from(WritableIndexBase.this.indexPath, path));
        }

        @Override
        public WritableIndexBase getIndex() {
            return WritableIndexBase.this;
        }

        @Override
        public NamespaceBase getNamespace() {
            return namespace;
        }

        @Override
        public boolean stillValid() {
            return valid;
        }

        @Override
        public boolean removeObjectFromIndex() {
            if (valid) {
                removeObject(this.namespace, this.path);
                this.object = null;
                this.path = null;
                this.valid = false;
                return true;
            }
            return false;
        }
    }

    protected class NamespaceBase implements WritableNamespace {

        protected final Map<StandardPathComponent, IndexReference> indexMap;

        protected IndexPathSection namespacePath = WritableIndexBase.this.indexPath;

        protected NamespaceBase() {
            indexMap = new HashMap<>();
        }

        @Override
        public Optional<IndexReference> addObject(FoxObject foxObject, ObjectPathSection path) {
            if (!this.indexMap.containsKey(path.getPathComponent())) {
                IndexReference ref = createReference(foxObject, path, this);
                foxObject.setIndexReference(ref);
                this.indexMap.put(path.getPathComponent(), ref);
                return Optional.of(ref);
            }
            return Optional.empty();
        }

        @Override
        public boolean isPathValid(StandardPathComponent component, boolean write) {
            return WritableIndexBase.this.isPathValid(component, write);
        }

        @Override
        public Optional<IndexReference> getObjectReference(StandardPathComponent path) {
            return Optional.ofNullable(this.indexMap.get(path));
        }

        @Override
        public Collection<StandardPathComponent> getAllObjectPaths() {
            return ImmutableSet.copyOf(this.indexMap.keySet());
        }

        @Override
        public IndexPathSection getIndexPath() {
            return namespacePath;
        }
    }
}
