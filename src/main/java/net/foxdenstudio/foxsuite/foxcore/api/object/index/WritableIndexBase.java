package net.foxdenstudio.foxsuite.foxcore.api.object.index;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxsuite.foxcore.api.object.link.node.LinkSlot;
import net.foxdenstudio.foxsuite.foxcore.api.object.link.schema.LinkSchema;
import net.foxdenstudio.foxsuite.foxcore.api.object.reference.types.*;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPath;
import net.foxdenstudio.foxsuite.foxcore.api.path.FoxPathFactory;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxsuite.foxcore.api.path.section.IndexPathSection;
import net.foxdenstudio.foxsuite.foxcore.api.path.section.LinkPathSection;
import net.foxdenstudio.foxsuite.foxcore.api.path.section.ObjectPathSection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

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

    protected final boolean registerNamespace(NamespaceBase namespace, @Nullable StandardPathComponent path) {
        return this.registerNamespace(namespace, path, false);
    }

    protected final boolean registerNamespace(NamespaceBase namespace, @Nullable StandardPathComponent path, boolean setDefault) {
        if (path == null) {
            if (!this.namelessNamespace) {
                this.namelessNamespace = true;
                this.defaultNamespace = namespace;
                this.defaultIndexMap = namespace.indexMap;
                return true;
            }
            return false;
        } else if (!this.namespaces.containsKey(path)) {
            if (!this.namelessNamespace && (setDefault || this.defaultNamespace == null)) this.defaultNamespace = namespace;
            this.namespaces.put(path, namespace);
            this.indexMapMap.put(path, namespace.indexMap);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Namespace> getNamespace(@Nonnull StandardPathComponent namespacePath) {
        if (namespacePath.isEmpty()) return Optional.of(this.defaultNamespace);
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
    public Optional<IndexReference> getObjectReference(StandardPathComponent path, @Nullable LinkPathSection links) {
        return this.defaultNamespace.getObjectReference(path, links);
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

    @Override
    public boolean contains(FoxObject object) {
        for (NamespaceBase namespace : this.namespaces.values()) {
            if (namespace.contains(object)) return true;
        }
        return false;
    }

    protected IndexReference createReference(FoxObject foxObject, ObjectPathSection path, NamespaceBase namespace) {
        return new IndexReferenceBase(foxObject, path, namespace);
    }

    protected EmbeddedIndexReference createdEmbeddedReference(FoxObject foxObject, ObjectPathSection path, NamespaceBase namespace, EmbedCapableIndexReference parent, StandardPathComponent embedPath) {
        return new EmbeddedIndexReferenceBase(foxObject, path, namespace, parent, embedPath);
    }

    protected EmbeddedLinkReference createdEmbeddedLinkReference(FoxObject foxObject, ObjectPathSection path, NamespaceBase namespace, EmbedCapableIndexReference parent, LinkSlot slot) {
        return new EmbeddedLinkReferenceBase(foxObject, path, namespace, parent, slot);
    }

    private boolean removeObject(NamespaceBase namespace, ObjectPathSection path) {
        return namespace.indexMap.remove(path.getPathComponent()) != null;
    }

    public class IndexReferenceBase implements WritableEmbedCapableIndexReference {

        protected FoxObject object;
        protected ObjectPathSection path;

        protected NamespaceBase namespace;
        protected Map<StandardPathComponent, EmbeddedIndexReference> embeddedObjects = null;

        protected transient Map<StandardPathComponent, EmbeddedIndexReference> embeddedObjectsCopy = null;
        protected transient FoxPath primaryPathCopy;
        protected transient boolean primaryPathCopyGenerated = false;

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
        public Optional<FoxPath> getPrimePath() {
            if (!primaryPathCopyGenerated) {
                this.primaryPathCopy = generatePrimePath().orElse(null);
                this.primaryPathCopyGenerated = true;
            }
            return Optional.ofNullable(this.primaryPathCopy);
        }

        protected Optional<FoxPath> generatePrimePath() {
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
        public boolean isEmbedded() {
            return false;
        }

        @Override
        public boolean isValid() {
            return valid;
        }

        @Override
        public boolean removeObjectFromIndex() {
            if (valid) {
                removeObject(this.namespace, this.path);
                this.object = null;
                this.path = null;
                this.valid = false;
                this.primaryPathCopyGenerated = false;
                this.primaryPathCopy = null;
                return true;
            }
            return false;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Optional<EmbeddedIndexReference> embedObject(FoxObject object, StandardPathComponent embedPath, boolean linked) {
            if (this.embeddedObjects.containsKey(embedPath) || WritableIndexBase.this.contains(object))
                return Optional.empty();
            if (linked) {
                LinkSlot slot = null;
                // TODO look up slot
                this.embeddedObjectsCopy = null;
                return ((Optional<EmbeddedIndexReference>) (Optional<? extends EmbeddedIndexReference>) embedObject(object, slot));
            } else {
                EmbeddedIndexReference reference = WritableIndexBase.this.createdEmbeddedReference(object, this.path, this.namespace, this, embedPath);
                if (reference == null) return Optional.empty();
                this.embeddedObjects.put(embedPath, reference);
                this.embeddedObjectsCopy = null;
                return Optional.of(reference);
            }
        }

        @Override
        public Optional<EmbeddedLinkReference> embedObject(FoxObject object, LinkSlot linkSlot) {
            return Optional.empty();
        }

        @Override
        public String toString() {
            return namespace.namespacePath.toString() + ":" + path.toString();
        }

        @Override
        public Map<StandardPathComponent, EmbeddedIndexReference> getEmbeddedObjects() {
            if (this.embeddedObjectsCopy == null) {
                if (this.embeddedObjects == null) {
                    this.embeddedObjectsCopy = ImmutableMap.of();
                } else {
                    this.embeddedObjectsCopy = ImmutableMap.copyOf(this.embeddedObjects);
                }
            }
            return this.embeddedObjectsCopy;
        }

        @Override
        public boolean contains(FoxObject foxObject) {
            for (EmbeddedIndexReference value : this.embeddedObjects.values()) {
                Optional<FoxObject> obj = value.getObject();
                if (obj.isPresent()) {
                    if (obj.get().equals(foxObject)) return true;
                }
                if (value instanceof EmbedCapableIndexReference) {
                    if (((EmbedCapableIndexReference) value).contains(foxObject)) return true;
                }
            }
            return false;
        }

    }

    public class EmbeddedIndexReferenceBase extends IndexReferenceBase implements EmbeddedIndexReference {

        protected EmbedCapableIndexReference parent;
        protected StandardPathComponent embedPath;
        protected LinkPathSection fullEmbedPath;

        protected EmbeddedIndexReferenceBase(FoxObject object, ObjectPathSection path, NamespaceBase namespace, EmbedCapableIndexReference parent, StandardPathComponent embedPath) {
            super(object, path, namespace);
            this.parent = parent;
            this.embedPath = embedPath;
            if (parent instanceof EmbeddedIndexReference) {
                this.fullEmbedPath = LinkPathSection.of(((EmbeddedIndexReference) parent).getFullEmbedPath(), embedPath);
            } else {
                this.fullEmbedPath = LinkPathSection.of(embedPath);
            }
        }

        @Override
        public EmbedCapableIndexReference getParent() {
            return this.parent;
        }

        @Override
        public StandardPathComponent getEmbedPath() {
            return this.embedPath;
        }

        @Override
        public LinkPathSection getFullEmbedPath() {
            return this.fullEmbedPath;
        }

        @Override
        public Optional<FoxPath> getPrimePath() {
            return Optional.ofNullable(path).map(path -> pathFactory.from(WritableIndexBase.this.indexPath, path, this.fullEmbedPath));
        }

        @Override
        protected Optional<FoxPath> generatePrimePath() {
            return Optional.ofNullable(path).map(path -> pathFactory.from(WritableIndexBase.this.indexPath, path, this.fullEmbedPath));
        }

        @Override
        public boolean removeObjectFromIndex() {
            if (valid) {
                boolean result = super.removeObjectFromIndex();
                if (result || !valid) {
                    this.embedPath = null;
                    this.fullEmbedPath = null;
                    this.parent = null;
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return namespace.namespacePath.toString() + ":" + path.toString() + "";
        }
    }

    public class EmbeddedLinkReferenceBase extends EmbeddedIndexReferenceBase implements EmbeddedLinkReference {

        private LinkSlot slot;
        private LinkSchema schema;

        protected EmbeddedLinkReferenceBase(FoxObject object, ObjectPathSection path, NamespaceBase namespace, EmbedCapableIndexReference parent, LinkSlot slot) {
            super(object, path, namespace, parent, slot.nodePath());
            this.slot = slot;
            this.schema = slot.getSchema();
        }

        @Override
        public Optional<LinkSlot> getLinkSlot() {
            return Optional.ofNullable(this.slot);
        }

        @Override
        public Optional<LinkSchema> getLinkSchema() {
            LinkSchema ret = null;
            if (slot != null) ret = this.slot.getSchema();
            if (ret == null) ret = this.schema;
            return Optional.ofNullable(ret);
        }

        @Override
        public StandardPathComponent slotPath() {
            StandardPathComponent ret = null;
            if (slot != null) ret = this.slot.nodePath();
            if (ret == null) ret = this.embedPath;
            return ret;
        }
    }

    protected class NamespaceBase implements WritableNamespace {

        protected final Map<StandardPathComponent, IndexReference> indexMap;
        protected transient Set<StandardPathComponent> allPathsCopy;

        protected IndexPathSection namespacePath = WritableIndexBase.this.indexPath;

        protected NamespaceBase() {
            indexMap = new HashMap<>();
        }

        @Override
        public Optional<IndexReference> addObject(FoxObject foxObject, ObjectPathSection path) {
            IndexReference existing = this.indexMap.get(path.getPathComponent());
            if(existing != null){
                if(existing.isValid()){
                    return Optional.empty();
                } else {
                    this.indexMap.remove(path.getPathComponent());
                }
            }
            IndexReference ref = createReference(foxObject, path, this);
            foxObject.setIndexReference(ref);
            this.indexMap.put(path.getPathComponent(), ref);
            this.allPathsCopy = null;
            return Optional.of(ref);
        }

        @Override
        public boolean isPathValid(StandardPathComponent component, boolean write) {
            return WritableIndexBase.this.isPathValid(component, write);
        }

        @Override
        public Optional<IndexReference> getObjectReference(StandardPathComponent path, @Nullable LinkPathSection links) {
            // TODO follow embedded objects.
            return Optional.ofNullable(this.indexMap.get(path));
        }

        @Override
        public Collection<StandardPathComponent> getAllObjectPaths() {
            if (this.allPathsCopy == null) {
                this.allPathsCopy = ImmutableSet.copyOf(this.indexMap.keySet());
            }
            return this.allPathsCopy;
        }

        @Override
        public IndexPathSection getIndexPath() {
            return namespacePath;
        }

        @Override
        public boolean contains(FoxObject foxObject) {
            for (IndexReference value : this.indexMap.values()) {
                Optional<FoxObject> obj = value.getObject();
                if (obj.isPresent()) {
                    if (obj.get().equals(foxObject)) return true;
                }

                if (value instanceof EmbedCapableIndexReference) {
                    if (((EmbedCapableIndexReference) value).contains(foxObject)) return true;
                }
            }
            return false;
        }
    }
}
