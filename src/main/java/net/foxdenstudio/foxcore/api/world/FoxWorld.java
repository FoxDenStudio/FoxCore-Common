package net.foxdenstudio.foxcore.api.world;

import net.foxdenstudio.foxcore.api.object.representation.FoxRepresentable;
import net.foxdenstudio.foxcore.api.object.representation.RepresentationObject;
import net.foxdenstudio.foxcore.api.util.FoxIdentifiable;
import net.foxdenstudio.foxcore.platform.world.World;

import java.nio.file.Path;
import java.util.Optional;

public interface FoxWorld extends FoxRepresentable, FoxIdentifiable {

    String getName();

    Optional<Path> getDirectory();

    Optional<World> getOnlineWorld();

    interface Object extends RepresentationObject<FoxWorld> {

    }
}
