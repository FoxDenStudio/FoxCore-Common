package net.foxdenstudio.foxsuite.foxcore.content.attribute;

import net.foxdenstudio.foxsuite.foxcore.api.attribute.BooleanAttribute;
import net.foxdenstudio.foxsuite.foxcore.content.attribute.value.EnabledAttrValue;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class EnabledAttribute extends BooleanAttribute<EnabledAttrValue> {

    private static final String NAME = "enabled";

    @Inject
    private EnabledAttribute(Provider<EnabledAttrValue> attrValueProvider){
        super(attrValueProvider, NAME, InheritanceMode.PROVISION);
    }
}
