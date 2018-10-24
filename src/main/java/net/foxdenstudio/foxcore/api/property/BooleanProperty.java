package net.foxdenstudio.foxcore.api.property;

import net.foxdenstudio.foxcore.api.attribute.BooleanAttribute;

public abstract class BooleanProperty<A extends BooleanAttribute<? extends BooleanProperty<A>>> extends BaseProperty<Boolean, A> {

    protected BooleanProperty(A attribute, Boolean initialValue) {
        super(attribute, initialValue);
    }

}
