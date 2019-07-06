package net.foxdenstudio.foxcore.content.archetype;

import net.foxdenstudio.foxcore.api.archetype.BaseArchetype;
import net.foxdenstudio.foxcore.content.attribute.ArchetypeDisplayNameAttribute;
import net.foxdenstudio.foxcore.content.attribute.value.ArchetypeDisplayNameAttrValue;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FoxObjectArchetype extends BaseArchetype {

    @Inject
    public FoxObjectArchetype(ArchetypeDisplayNameAttribute archetypeDisplayNameAttribute) {
        super("object", "Object", archetypeDisplayNameAttribute);
        this.writeDefaultName(archetypeDisplayNameAttribute);
    }
}
