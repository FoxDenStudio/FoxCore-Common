package net.foxdenstudio.foxcore.api.object.link.node;

import net.foxdenstudio.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxcore.api.object.link.LinkSchema;
import net.foxdenstudio.foxcore.api.object.reference.types.IndexReference;
import net.foxdenstudio.foxcore.api.object.reference.types.LinkReference;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class LinkSlotBase extends LinkNodeBase implements LinkSlot {

    @Nullable
    protected LinkReference linked;

    protected LinkSlotBase(FoxObject containerObject, LinkSchema schema, LinkNodeContainer parent, StandardPathComponent nodePath, int localPathLength, boolean dynamic, boolean embeddable) {
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
        if (path != null) return super.linkObject(object, path);
        if (!this.acceptsObject(object)) return Optional.empty();
        if (this.linked == null) {

        }
        return Optional.empty();
    }


}
