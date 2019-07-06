package net.foxdenstudio.foxcore.content.attribute.value;

import net.foxdenstudio.foxcore.api.attribute.value.BooleanAttrValue;
import net.foxdenstudio.foxcore.content.attribute.EnabledAttribute;

import javax.inject.Inject;

public class EnabledAttrValue extends BooleanAttrValue<EnabledAttribute> {

    @Inject
    public EnabledAttrValue(EnabledAttribute attribute) {
        super(attribute, true);
    }
}
