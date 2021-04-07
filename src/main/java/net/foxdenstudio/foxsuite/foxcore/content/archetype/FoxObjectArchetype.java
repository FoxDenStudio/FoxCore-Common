package net.foxdenstudio.foxsuite.foxcore.content.archetype;

import net.foxdenstudio.foxsuite.foxcore.api.archetype.ArchetypeBase;
import net.foxdenstudio.foxsuite.foxcore.content.attribute.ArchetypeDisplayNameAttribute;

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
