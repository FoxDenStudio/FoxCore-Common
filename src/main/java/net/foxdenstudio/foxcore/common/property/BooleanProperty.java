package net.foxdenstudio.foxcore.common.property;

import net.foxdenstudio.foxcore.common.attribute.BooleanAttribute;

public abstract class BooleanProperty<A extends BooleanAttribute<? extends BooleanProperty<A>>> extends BaseProperty<Boolean, A> {

    protected BooleanProperty(A attribute, Boolean initialValue) {
        super(attribute, initialValue);
    }

}
