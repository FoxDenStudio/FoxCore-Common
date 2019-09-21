package net.foxdenstudio.foxcore.content.archetype;

import net.foxdenstudio.foxcore.api.archetype.ArchetypeBase;
import net.foxdenstudio.foxcore.content.attribute.ArchetypeDisplayNameAttribute;
import net.foxdenstudio.foxcore.content.attribute.EnabledAttribute;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RegionArchetype extends ArchetypeBase {

    @Inject
    private RegionArchetype(FoxObjectArchetype foxObjectArchetype,
                           ArchetypeDisplayNameAttribute archetypeDisplayNameAttribute,
                           EnabledAttribute enabledAttribute) {
        super("region", "Region", foxObjectArchetype, archetypeDisplayNameAttribute, enabledAttribute);
        this.writeDefaultName(archetypeDisplayNameAttribute);
    }

}
