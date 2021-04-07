package net.foxdenstudio.foxsuite.foxcore.platform.world;

import net.foxdenstudio.foxsuite.foxcore.platform.world.extent.Extent;

public interface Location<E extends Extent> {

    E getExtent();

}
