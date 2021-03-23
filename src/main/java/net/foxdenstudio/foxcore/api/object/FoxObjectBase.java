package net.foxdenstudio.foxcore.api.object;

import com.google.common.collect.ImmutableSet;
import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxcore.api.attribute.holder.AttributeContainer;
import net.foxdenstudio.foxcore.api.attribute.holder.AttributeHolder;
import net.foxdenstudio.foxcore.api.attribute.holder.DelegateAttributeHolder;
import net.foxdenstudio.foxcore.api.object.link.LinkContainer;
import net.foxdenstudio.foxcore.api.object.link.LinkResult;
import net.foxdenstudio.foxcore.api.object.link.node.FoxBasicLinkNodeContainer;
import net.foxdenstudio.foxcore.api.object.reference.types.IndexReference;
import net.foxdenstudio.foxcore.api.path.component.StandardPathComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

public abstract class FoxObjectBase<A extends FoxArchetype> implements FoxObject, DelegateAttributeHolder {

    protected final A archetype;
    protected final AttributeContainer attributeContainer;
    protected final LinkContainer linkContainer;

    protected IndexReference indexReference;

    protected FoxObjectBase(A archetype, FoxAttribute<?>... attributes) {
        this.archetype = archetype;
        this.attributeContainer = new AttributeContainer(archetype, true, attributes);
        this.linkContainer = new FoxBasicLinkNodeContainer(this);
    }

    @Override
    public Optional<IndexReference> getIndexReference() {
        return Optional.ofNullable(indexReference);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setIndexReference(IndexReference indexReference) {
        if (indexReference.isValid() && indexReference.getObject().isPresent() && indexReference.getObject().get() == this) {
            this.indexReference = (IndexReference) indexReference;
        } else throw new IllegalArgumentException("Index reference must be valid and refer to this object.");
    }

    @Override
    public A getArchetype() {
        return this.archetype;
    }

    @Override
    public AttributeHolder getDelegateAttrHolder() {
        return this.attributeContainer;
    }

    @Override
    public LinkResult link(@Nonnull FoxObject object, @Nullable StandardPathComponent linkPath) {
        this.linkContainer.linkObject(object, linkPath);
        return null;
    }

    @Override
    public LinkContainer getLinkContainer() {
        return this.linkContainer;
    }

    public Set<FoxObject> getLinks(@Nullable StandardPathComponent path) {

        return ImmutableSet.of();
    }
}
