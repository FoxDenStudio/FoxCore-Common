package net.foxdenstudio.foxcore.api.object.link.node;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.link.schema.LinkSchema;
import net.foxdenstudio.foxcore.api.object.reference.FoxObjectReference;
import net.foxdenstudio.foxcore.api.object.reference.types.IndexReference;
import net.foxdenstudio.foxcore.api.object.reference.types.LinkReference;
import net.foxdenstudio.foxcore.api.object.reference.types.ProxyLinkReference;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class LinkSlotBase extends LinkNodeBase implements LinkSlot {

    @Nullable
    protected LinkReference linked;

    protected LinkSlotBase(
            FoxObject containerObject,
            LinkSchema schema,
            LinkNodeContainer parent,
            StandardPathComponent nodePath,
            int localPathLength,
            boolean dynamic,
            boolean embeddable) {
        super(containerObject, schema, parent, nodePath, localPathLength, dynamic, embeddable);
    }

    @Override
    public boolean hasLink() {
        return this.linked != null;
    }

    @Override
    public Optional<LinkReference> getLinkedObject() {
        return Optional.ofNullable(this.linked);
    }

    @Override
    public boolean acceptsObject(@Nonnull FoxObject object, @Nullable StandardPathComponent path) {
        Optional<IndexReference> refOpt = object.getIndexReference();
        return refOpt.isPresent() && refOpt.get().isValid();
    }

    @Override
    public Optional<LinkReference> linkObject(@Nonnull FoxObject object, @Nullable StandardPathComponent path) {
        if (path != null && !path.isEmpty()) return super.linkObject(object, path);
        if (!this.acceptsObject(object)) return Optional.empty();
        if (this.linked == null) {
            // TODO construct a link reference
            // I have only been working with index references and i'm not sure how i'm going to do link references.
            Optional<IndexReference> indexRefOpt = object.getIndexReference();
            if (indexRefOpt.isPresent()) {
                LinkReference lr = new BasicLinkRef(indexRefOpt.get());
                this.linked = lr;
                return Optional.of(lr);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<FoxObjectReference> removeObject() {
        LinkReference lr = this.linked;
        if (lr == null) return Optional.empty();
        this.linked = null;
        return lr instanceof ProxyLinkReference
                ? ((ProxyLinkReference) lr).getTargetReference()
                : Optional.of(lr);
    }

    public class BasicLinkRef implements ProxyLinkReference {

        FoxObjectReference objectReference;

        public BasicLinkRef(FoxObjectReference objectReference) {
            this.objectReference = objectReference;
        }

        @Override
        public Optional<LinkSlot> getLinkSlot() {
            return Optional.of(LinkSlotBase.this);
        }

        @Override
        public Optional<LinkSchema> getLinkSchema() {
            return Optional.empty();
        }

        @Override
        public StandardPathComponent slotPath() {
            return LinkSlotBase.this.nodePath;
        }

        @Override
        public boolean isEmbedded() {
            return false;
        }

        @Override
        public boolean unlink() {
            return LinkSlotBase.this.removeObject().isPresent();
        }

        @Override
        public Optional<FoxObjectReference> getTargetReference() {
            return Optional.ofNullable(this.objectReference);
        }
    }


}
