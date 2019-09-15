package net.foxdenstudio.foxcore.api.archetype.container;

import net.foxdenstudio.foxcore.api.archetype.FoxArchetype;

import java.util.List;

public interface ArchetypeContainer {

    FoxArchetype getPrimaryArchetype();

    List<FoxArchetype> getSecondaryArchetypes();

    List<FoxArchetype> getTertiaryArchetypes();
}
