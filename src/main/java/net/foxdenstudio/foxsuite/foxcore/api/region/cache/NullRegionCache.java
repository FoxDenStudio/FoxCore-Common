package net.foxdenstudio.foxsuite.foxcore.api.region.cache;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.collect.ImmutableList;
import net.foxdenstudio.foxsuite.foxcore.api.object.FoxObject;
import net.foxdenstudio.foxsuite.foxcore.api.object.link.LinkContainer;
import net.foxdenstudio.foxsuite.foxcore.api.object.link.node.LinkNode;
import net.foxdenstudio.foxsuite.foxcore.api.object.link.node.LinkSlot;
import net.foxdenstudio.foxsuite.foxcore.api.object.reference.types.LinkReference;
import net.foxdenstudio.foxsuite.foxcore.api.path.component.StandardPathComponent;
import net.foxdenstudio.foxsuite.foxcore.api.region.FoxRegion;
import net.foxdenstudio.foxsuite.foxcore.api.world.FoxWorld;
import net.foxdenstudio.foxsuite.foxcore.api.world.FoxWorldManager;
import net.foxdenstudio.foxsuite.foxcore.platform.world.World;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

@Singleton
public class NullRegionCache implements RegionCache {

    public static final StandardPathComponent REGION_PATH = StandardPathComponent.of("r");

    private final FoxWorldManager worldManager;

    @Inject
    public NullRegionCache(FoxWorldManager worldManager) {
        this.worldManager = worldManager;
    }


    @Override
    public Collection<FoxRegion> getRegions(Vector3d pos, World world) {
        return this.getRegions(foxRegion -> foxRegion.contains(pos.getX(), pos.getY(), pos.getZ()), world);
    }

    @Override
    public Collection<FoxRegion> getRegionsBlock(Vector3i blockPos, World world) {
        return this.getRegions(foxRegion -> foxRegion.containsBlock(blockPos.getX(), blockPos.getY(), blockPos.getZ()), world);
    }

    protected Collection<FoxRegion> getRegions(Predicate<FoxRegion> check, World world) {
        FoxWorld foxWorld = worldManager.getWorld(world);
        Optional<FoxWorld.Object> repOpt = (Optional<FoxWorld.Object>) foxWorld.getRepresentation();
        if (repOpt.isPresent()) {
            FoxWorld.Object rep = repOpt.get();
            LinkContainer linkContainer = rep.getLinkContainer();
            Optional<LinkNode> nodeOpt = linkContainer.getNode(REGION_PATH);
            if (nodeOpt.isPresent()) {
                LinkNode node = nodeOpt.get();
                ImmutableList.Builder<FoxRegion> found = ImmutableList.builder();
                for (LinkNode value : node.getKnownNodes().values()) {
                    if (value instanceof LinkSlot) {
                        Optional<LinkReference> linkOpt = ((LinkSlot) value).getLinkedObject();
                        if (linkOpt.isPresent()) {
                            Optional<FoxObject> foxObjectOpt = linkOpt.get().getObject();
                            if (foxObjectOpt.isPresent()) {
                                FoxObject foxObject = foxObjectOpt.get();
                                if (foxObject instanceof FoxRegion) {
                                    FoxRegion region = (FoxRegion) foxObject;
                                    if (check.test(region)) {
                                        found.add(region);
                                    }
                                }
                            }
                        }
                    }
                }
                return found.build();
            }
        }
        return ImmutableList.of();
    }
}
