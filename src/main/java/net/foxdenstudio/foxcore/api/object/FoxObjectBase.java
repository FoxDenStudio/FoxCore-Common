package net.foxdenstudio.foxcore.api.object;

import net.foxdenstudio.foxcore.api.attribute.holder.AttributeContainer;
import net.foxdenstudio.foxcore.api.attribute.holder.DelegateAttributeHolder;
import net.foxdenstudio.foxcore.api.object.reference.IndexReference;

import java.util.Optional;

public abstract class FoxObjectBase<T extends FoxObjectBase<T>> implements FoxObject<T>, DelegateAttributeHolder {

    protected final AttributeContainer attributeContainer = new AttributeContainer();

    protected IndexReference<T> indexReference;

    @Override
    public Optional<IndexReference<T>> getIndexReference() {
        return Optional.ofNullable(indexReference);
    }

    @Override
    public void setIndexReference(IndexReference<T> indexReference) {
        if (!(indexReference.stillValid() && indexReference.getObject().isPresent() && indexReference.getObject().get() == this))
            throw new IllegalArgumentException("Index reference must be valid and refer to this object.");
        this.indexReference = indexReference;
    }
}
