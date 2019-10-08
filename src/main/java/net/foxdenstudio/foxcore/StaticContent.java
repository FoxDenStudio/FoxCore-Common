package net.foxdenstudio.foxcore;

import net.foxdenstudio.foxcore.content.command.*;
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
    CommandDetail commandDetail;

    @Inject
    QubeRegion.RectGenerator generatorRegionRect;
    @Inject
    QubeRegion.BoxGenerator generatorRegionBox;
    @Inject
    QubeRegion.FLARDGenerator generatorRegionFlard;
}
