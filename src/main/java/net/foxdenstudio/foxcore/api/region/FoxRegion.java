package net.foxdenstudio.foxcore.api.region;

import net.foxdenstudio.foxcore.api.object.FoxObject;

public interface FoxRegion<T extends FoxRegion<T>> extends FoxObject<T> {

    boolean contains(int x, int y, int z);

}
