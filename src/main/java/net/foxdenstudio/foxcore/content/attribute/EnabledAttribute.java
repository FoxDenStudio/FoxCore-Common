package net.foxdenstudio.foxcore.content.attribute;

import net.foxdenstudio.foxcore.api.attribute.BooleanAttribute;
import net.foxdenstudio.foxcore.content.attribute.value.EnabledAttrValue;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class EnabledAttribute extends BooleanAttribute<EnabledAttrValue> {

    private static final String NAME = "enabled";

    @Inject
    public EnabledAttribute(Provider<EnabledAttrValue> attrValueProvider){
        super(attrValueProvider, NAME);
    }
}
