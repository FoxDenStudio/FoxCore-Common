package net.foxdenstudio.foxsuite.foxcore.api.archetype.container;

import net.foxdenstudio.foxsuite.foxcore.api.archetype.FoxArchetype;

import java.util.List;

public interface ArchetypeContainer {

    FoxArchetype getPrimaryArchetype();

    List<FoxArchetype> getSecondaryArchetypes();

    List<FoxArchetype> getTertiaryArchetypes();
}
