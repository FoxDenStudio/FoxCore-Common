package net.foxdenstudio.foxcore.content.archetype;

import net.foxdenstudio.foxcore.api.archetype.BaseArchetype;
import net.foxdenstudio.foxcore.content.attribute.ArchetypeDisplayNameAttribute;
import net.foxdenstudio.foxcore.content.attribute.EnabledAttribute;

import javax.inject.Inject;

public class RegionArchetype extends BaseArchetype {

    @Inject
    private RegionArchetype(FoxObjectArchetype foxObjectArchetype,
                           ArchetypeDisplayNameAttribute archetypeDisplayNameAttribute,
                           EnabledAttribute enabledAttribute) {
        super("region", "Region", foxObjectArchetype, archetypeDisplayNameAttribute, enabledAttribute);
        this.writeDefaultName(archetypeDisplayNameAttribute);
    }
}
