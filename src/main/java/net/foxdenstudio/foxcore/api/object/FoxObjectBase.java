package net.foxdenstudio.foxcore.api.object;

import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxcore.api.attribute.holder.AttributeContainer;
import net.foxdenstudio.foxcore.api.attribute.holder.AttributeHolder;
import net.foxdenstudio.foxcore.api.attribute.holder.DelegateAttributeHolder;
import net.foxdenstudio.foxcore.api.object.reference.IndexReference;

import java.util.Optional;

public abstract class FoxObjectBase<T extends FoxObjectBase<T>> implements FoxObject<T>, DelegateAttributeHolder {

    protected final FoxArchetype archetype;
    protected final AttributeContainer attributeContainer;

    protected IndexReference<T> indexReference;

    protected FoxObjectBase(FoxArchetype archetype, FoxAttribute<?>... attributes){
        this.archetype = archetype;
        this.attributeContainer = new AttributeContainer(archetype, true, attributes);
    }

    @Override
    public Optional<IndexReference<T>> getIndexReference() {
        return Optional.ofNullable(indexReference);
    }

    @Override
    public void setIndexReference(IndexReference<T> indexReference) {
        if (indexReference.stillValid() && indexReference.getObject().isPresent() && indexReference.getObject().get() == this) {
            this.indexReference = indexReference;
        } else throw new IllegalArgumentException("Index reference must be valid and refer to this object.");
    }

    @Override
    public FoxArchetype getArchetype() {
        return this.archetype;
    }

    @Override
    public AttributeHolder getDelegateAttrHolder() {
        return this.attributeContainer;
    }
}
