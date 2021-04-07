package net.foxdenstudio.foxsuite.foxcore.content.archetype;

import net.foxdenstudio.foxsuite.foxcore.api.archetype.ArchetypeBase;
import net.foxdenstudio.foxsuite.foxcore.content.attribute.ArchetypeDisplayNameAttribute;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GeneratorArchetype extends ArchetypeBase {

    @Inject
    private GeneratorArchetype(FoxObjectArchetype foxObjectArchetype,
                               ArchetypeDisplayNameAttribute archetypeDisplayNameAttribute) {
        super("generator", "Generator", foxObjectArchetype, archetypeDisplayNameAttribute);
        this.writeDefaultName(archetypeDisplayNameAttribute);
    }

}
