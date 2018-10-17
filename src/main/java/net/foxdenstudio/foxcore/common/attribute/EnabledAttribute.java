package net.foxdenstudio.foxcore.common.attribute;

import net.foxdenstudio.foxcore.common.property.EnabledProperty;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class EnabledAttribute extends BooleanAttribute<EnabledProperty> {

    public static final String NAME = "enabled";

    @Inject
    public EnabledAttribute(Provider<EnabledProperty> propertyProvider){
        super(propertyProvider, NAME);
    }
}
