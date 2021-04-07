package net.foxdenstudio.foxsuite.foxcore.api.region.cache;

import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import net.foxdenstudio.foxsuite.foxcore.api.region.FoxRegion;
import net.foxdenstudio.foxsuite.foxcore.api.world.FoxWorldManager;
import net.foxdenstudio.foxsuite.foxcore.platform.world.World;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.stream.Stream;

@Singleton
public class RegionCacheImpl2D implements RegionCache {

    private static final int CHUNK_WIDTH_EX = 4;
    private static final int SECTION_WIDTH_EX = 5;
    private static final int COMBINED_EX = CHUNK_WIDTH_EX + SECTION_WIDTH_EX;   // 9

    private static final int CHUNK_WIDTH = 2 << CHUNK_WIDTH_EX;                 // 16
    private static final int SECTION_WIDTH = 2 << SECTION_WIDTH_EX;             // 32

    private final FoxWorldManager worldManager;

    private final Map<World, WorldCache> worldCaches = new WeakHashMap<>();

    @Inject
    public RegionCacheImpl2D(FoxWorldManager worldManager) {
        this.worldManager = worldManager;
    }


    @Override
    public Collection<FoxRegion> getRegions(Vector3d pos, World world) {
        WorldCache worldCache = this.worldCaches.get(world);
        if (worldCache == null) {
            worldCache = new WorldCache(world);
            this.worldCaches.put(world, worldCache);
        }
        return worldCache.getRegions(pos);
    }

    @Override
    public Collection<FoxRegion> getRegionsBlock(Vector3i blockPos, World world) {
        return null;
    }

    public static class WorldCache {

        private final World world;

        private Map<Vector2i, CacheSection> sectionMap = new HashMap<>();

        public WorldCache(World world) {
            this.world = world;
        }

        public Collection<FoxRegion> getRegions(Vector3d pos) {
            final int chunkX = pos.getFloorX() >> CHUNK_WIDTH_EX;
            final int chunkZ = pos.getFloorZ() >> CHUNK_WIDTH_EX;
            final Vector2i secCoord = Vector2i.from(chunkX >> SECTION_WIDTH_EX, chunkZ >> SECTION_WIDTH_EX);
            CacheSection section = this.sectionMap.get(secCoord);
            if (section == null) {
                section = new CacheSection();
                this.sectionMap.put(secCoord, section);
            }
            return section.getRegions(pos, chunkX, chunkZ);
        }

        public class CacheSection {

            private final CacheChunk[] chunks = new CacheChunk[SECTION_WIDTH * SECTION_WIDTH];

            Collection<FoxRegion> getRegions(Vector3d pos, int chunkX, int chunkZ) {
                final int localChunkX = chunkX & SECTION_WIDTH_EX;
                final int localChunkZ = chunkZ & SECTION_WIDTH_EX;
                final int index = localChunkX * SECTION_WIDTH + localChunkZ;
                CacheChunk chunk = this.chunks[index];
                if (chunk == null) {
                    chunk = new CacheChunk();
                }
                return chunk.regions;
            }

            public class CacheChunk {
                Collection<FoxRegion> regions;
                boolean dirty;


            }
        }
    }

    public static class CacheResult {

        public Collection<FoxRegion> cached;
        public Collection<FoxRegion> uncached;

        public Stream<FoxRegion> regionStream() {
            return Stream.of(this.cached, this.cached).flatMap(Collection::stream);
        }

        public Stream<FoxRegion> regionStreamFiltered() {
            return Stream.concat(cached.stream(), uncached.stream());
        }
    }
}
