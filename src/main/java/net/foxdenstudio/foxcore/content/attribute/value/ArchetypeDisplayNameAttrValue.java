package net.foxdenstudio.foxcore.content.attribute.value;

import net.foxdenstudio.foxcore.api.attribute.value.StringAttrValue;
import net.foxdenstudio.foxcore.content.attribute.ArchetypeDisplayNameAttribute;

import javax.inject.Inject;

public class ArchetypeDisplayNameAttrValue extends StringAttrValue<ArchetypeDisplayNameAttribute> {

    @Inject
    protected ArchetypeDisplayNameAttrValue(ArchetypeDisplayNameAttribute attribute) {
        super(attribute, "undefined");
    }
}
