package net.foxdenstudio.foxsuite.foxcore.api.region.cache;

import net.foxdenstudio.foxsuite.foxcore.platform.util.Tristate;

public interface ICacheable {

    Tristate isPresentAtChunk(int chunkX, int chunkY, int chunkSize);
}
