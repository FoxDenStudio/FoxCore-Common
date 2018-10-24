package net.foxdenstudio.foxcore.platform.world;

import net.foxdenstudio.foxcore.platform.world.extent.Extent;

public interface Location<E extends Extent> {

    E getExtent();

}
