package net.foxdenstudio.foxcore;

import net.foxdenstudio.foxcore.content.command.CommandEcho;
import net.foxdenstudio.foxcore.content.command.CommandList;
import net.foxdenstudio.foxcore.content.command.CommandPath;
import net.foxdenstudio.foxcore.content.region.RectangularRegion;

import javax.inject.Inject;

public class StaticContent {

    @Inject
    CommandEcho commandEcho;
    @Inject
    CommandPath commandPath;
    @Inject
    CommandList commandList;

    @Inject
    RectangularRegion.Generator generatorRectRegion;
}
