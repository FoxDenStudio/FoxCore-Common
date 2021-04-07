package net.foxdenstudio.foxsuite.foxcore;

import net.foxdenstudio.foxsuite.foxcore.api.world.FoxWorld;
import net.foxdenstudio.foxsuite.foxcore.content.archetype.GeneratorArchetype;
import net.foxdenstudio.foxsuite.foxcore.content.archetype.RepresentationArchetype;
import net.foxdenstudio.foxsuite.foxcore.content.command.*;
import net.foxdenstudio.foxsuite.foxcore.content.region.QubeRegion;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FCStaticContent {

    @Inject
    CommandCD commandCD;
    @Inject
    CommandPWD commandPWD;
    @Inject
    CommandList commandList;
    @Inject
    CommandNew commandNew;
    @Inject
    CommandDelete commandDelete;
    @Inject
    CommandDetail commandDetail;
    @Inject
    CommandLink commandLink;

    @Inject
    CommandSave commandSave;

    @Inject
    CommandStringChar commandStringChar;
    @Inject
    CommandEcho commandEcho;
    @Inject
    CommandPath commandPath;
    @Inject
    DebugSystem debugSystem;

    @Inject
    QubeRegion.RectGenerator generatorRegionRect;
    @Inject
    QubeRegion.BoxGenerator generatorRegionBox;
    @Inject
    QubeRegion.FLARDGenerator generatorRegionFlard;

    @Inject
    QubeRegion.Type qubeRegionType;
    @Inject
    RepresentationArchetype representationArchetype;
    @Inject
    GeneratorArchetype generatorArchetype;
    @Inject
    FoxWorld.Type worldArchetype;
}
