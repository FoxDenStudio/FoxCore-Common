package net.foxdenstudio.foxcore.content.archetype;

import net.foxdenstudio.foxcore.api.archetype.ArchetypeBase;
import net.foxdenstudio.foxcore.content.attribute.ArchetypeDisplayNameAttribute;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RepresentationArchetype extends ArchetypeBase {

    @Inject
    private RepresentationArchetype(FoxObjectArchetype foxObjectArchetype,
                                    ArchetypeDisplayNameAttribute archetypeDisplayNameAttribute){
        super("representation", "Representation", foxObjectArchetype, archetypeDisplayNameAttribute);
        writeDefaultName(archetypeDisplayNameAttribute);
    }
}
