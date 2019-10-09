package net.foxdenstudio.foxcore.api.object;

import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxcore.api.attribute.holder.AttributeContainer;
import net.foxdenstudio.foxcore.api.attribute.holder.AttributeHolder;
import net.foxdenstudio.foxcore.api.attribute.holder.DelegateAttributeHolder;
import net.foxdenstudio.foxcore.api.object.reference.IndexReference;

import java.util.Optional;

public abstract class FoxObjectBase<A extends FoxArchetype> implements FoxObject, DelegateAttributeHolder {

    protected final A archetype;
    protected final AttributeContainer attributeContainer;

    protected IndexReference indexReference;

    protected FoxObjectBase(A archetype, FoxAttribute<?>... attributes){
        this.archetype = archetype;
        this.attributeContainer = new AttributeContainer(archetype, true, attributes);
    }

    @Override
    public Optional<IndexReference> getIndexReference() {
        return Optional.ofNullable(indexReference);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setIndexReference(IndexReference indexReference) {
        if (indexReference.stillValid() && indexReference.getObject().isPresent() && indexReference.getObject().get() == this) {
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

}
