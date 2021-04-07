package net.foxdenstudio.foxsuite.foxcore.api.region.cache;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import net.foxdenstudio.foxsuite.foxcore.api.region.FoxRegion;
import net.foxdenstudio.foxsuite.foxcore.platform.world.World;

import java.util.Collection;

public interface RegionCache {

    Collection<FoxRegion> getRegions(Vector3d pos, World world);

    Collection<FoxRegion> getRegionsBlock(Vector3i blockPos, World world);
}
