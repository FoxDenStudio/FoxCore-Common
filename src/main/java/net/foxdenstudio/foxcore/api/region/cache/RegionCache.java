package net.foxdenstudio.foxcore.api.region.cache;

import com.flowpowered.math.vector.Vector3d;
import net.foxdenstudio.foxcore.api.region.FoxRegion;
import net.foxdenstudio.foxcore.platform.world.World;

import java.util.Collection;

public interface RegionCache {

    Collection<FoxRegion> getRegions(Vector3d pos, World world);
}
