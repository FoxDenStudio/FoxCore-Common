package net.foxdenstudio.foxcore.content.attribute;

import net.foxdenstudio.foxcore.api.attribute.StringAttribute;
import net.foxdenstudio.foxcore.content.attribute.value.ArchetypeDisplayNameAttrValue;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class ArchetypeDisplayNameAttribute extends StringAttribute<ArchetypeDisplayNameAttrValue> {

    @Inject
    private ArchetypeDisplayNameAttribute(Provider<ArchetypeDisplayNameAttrValue> attrValueProvider) {
        super(attrValueProvider, "archetype-display-name", InheritanceMode.NONE);
    }
}
