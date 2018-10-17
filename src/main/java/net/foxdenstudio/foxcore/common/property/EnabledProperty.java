package net.foxdenstudio.foxcore.common.property;

import net.foxdenstudio.foxcore.common.attribute.EnabledAttribute;

import javax.inject.Inject;

public class EnabledProperty extends BooleanProperty<EnabledAttribute> {

    @Inject
    public EnabledProperty(EnabledAttribute attribute) {
        super(attribute, false);
    }
}
