package net.foxdenstudio.foxsuite.foxcore.content.archetype;

import net.foxdenstudio.foxsuite.foxcore.api.archetype.ArchetypeBase;
import net.foxdenstudio.foxsuite.foxcore.content.attribute.ArchetypeDisplayNameAttribute;
import net.foxdenstudio.foxsuite.foxcore.content.attribute.EnabledAttribute;

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
