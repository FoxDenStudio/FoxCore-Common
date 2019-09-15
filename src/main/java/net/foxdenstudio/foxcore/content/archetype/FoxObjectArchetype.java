package net.foxdenstudio.foxcore.content.archetype;

import net.foxdenstudio.foxcore.api.archetype.ArchetypeBase;
import net.foxdenstudio.foxcore.content.attribute.ArchetypeDisplayNameAttribute;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FoxObjectArchetype extends ArchetypeBase {

    @Inject
    private FoxObjectArchetype(ArchetypeDisplayNameAttribute archetypeDisplayNameAttribute) {
        super("object", "Object", archetypeDisplayNameAttribute);
        this.writeDefaultName(archetypeDisplayNameAttribute);
    }
}
