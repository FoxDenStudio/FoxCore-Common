package net.foxdenstudio.foxcore.platform.world;

import net.foxdenstudio.foxcore.platform.util.Identifiable;
import net.foxdenstudio.foxcore.platform.world.extent.Extent;

import java.nio.file.Path;

public interface World extends Extent, Identifiable {

    String getName();

    Path getDirectory();

}
