package net.foxdenstudio.foxcore.common.platform.world;

public interface Locatable {

    Location<World> getLocation();

    /**
     * Gets the world that this source resides in.
     *
     * @return The World
     */
    World getWorld();

}
