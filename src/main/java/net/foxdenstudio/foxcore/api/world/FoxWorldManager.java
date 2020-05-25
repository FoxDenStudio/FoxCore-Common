package net.foxdenstudio.foxcore.api.world;

import net.foxdenstudio.foxcore.platform.world.World;

import java.util.Map;
import java.util.Optional;

public interface FoxWorldManager {

    Optional<World> getOnlineWorld(String name);

    Optional<FoxWorld> getWorld(String name);

    Map<String, World> getOnlineWorlds();

    Map<String, FoxWorld> getWorlds();

    void load();

    void save();
}
