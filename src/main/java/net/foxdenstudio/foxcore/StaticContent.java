package net.foxdenstudio.foxcore;

import net.foxdenstudio.foxcore.content.command.CommandEcho;
import net.foxdenstudio.foxcore.content.command.CommandList;
import net.foxdenstudio.foxcore.content.command.CommandNew;
import net.foxdenstudio.foxcore.content.command.CommandPath;
import net.foxdenstudio.foxcore.content.region.QubeRegion;

import javax.inject.Inject;

public class StaticContent {

    @Inject
    CommandEcho commandEcho;
    @Inject
    CommandPath commandPath;
    @Inject
    CommandList commandList;
    @Inject
    CommandNew commandNew;

    @Inject
    QubeRegion.RectGenerator generatorRegionRect;
    @Inject
    QubeRegion.BoxGenerator generatorRegionBox;
}
