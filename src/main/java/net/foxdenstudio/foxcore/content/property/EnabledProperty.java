package net.foxdenstudio.foxcore.content.property;

import net.foxdenstudio.foxcore.api.property.BooleanProperty;
import net.foxdenstudio.foxcore.content.attribute.EnabledAttribute;

import javax.inject.Inject;

public class EnabledProperty extends BooleanProperty<EnabledAttribute> {

    @Inject
    public EnabledProperty(EnabledAttribute attribute) {
        super(attribute, false);
    }
}
