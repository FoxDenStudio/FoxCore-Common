package net.foxdenstudio.foxcore.api.region;

import net.foxdenstudio.foxcore.api.object.FoxObject;

public interface FoxRegion extends FoxObject {

    boolean containsBlock(int x, int y, int z);

    boolean contains(double x, double y, double z);
}
