package net.foxdenstudio.foxcore.content.archetype;

import net.foxdenstudio.foxcore.api.archetype.ArchetypeBase;
import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;
import net.foxdenstudio.foxcore.api.attribute.FoxAttribute;
import net.foxdenstudio.foxcore.content.attribute.ArchetypeDisplayNameAttribute;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;

@Singleton
public class GeneratorArchetype extends ArchetypeBase {

    @Inject
    private GeneratorArchetype(FoxObjectArchetype foxObjectArchetype,
                               ArchetypeDisplayNameAttribute archetypeDisplayNameAttribute) {
        super("generator", "Generator", foxObjectArchetype, archetypeDisplayNameAttribute);
        this.writeDefaultName(archetypeDisplayNameAttribute);
    }

}
