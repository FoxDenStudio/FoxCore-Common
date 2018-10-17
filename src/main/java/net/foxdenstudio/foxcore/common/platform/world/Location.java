package net.foxdenstudio.foxcore.common.platform.world;

import net.foxdenstudio.foxcore.common.platform.world.extent.Extent;

public interface Location<E extends Extent> {

    E getExtent();

}
