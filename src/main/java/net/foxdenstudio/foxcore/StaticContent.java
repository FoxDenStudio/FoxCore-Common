package net.foxdenstudio.foxcore;

import net.foxdenstudio.foxcore.api.world.FoxWorld;
import net.foxdenstudio.foxcore.content.archetype.GeneratorArchetype;
import net.foxdenstudio.foxcore.content.archetype.RepresentationArchetype;
import net.foxdenstudio.foxcore.content.command.*;
import net.foxdenstudio.foxcore.content.region.QubeRegion;

import javax.inject.Inject;

public class StaticContent {

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
