package net.foxdenstudio.foxcore.api.region.cache;

import net.foxdenstudio.foxcore.platform.util.Tristate;

public interface ICacheable {

    Tristate isPresentAtChunk(int chunkX, int chunkY, int chunkSize);
}
