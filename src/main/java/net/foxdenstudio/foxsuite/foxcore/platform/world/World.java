package net.foxdenstudio.foxsuite.foxcore.platform.world;

import net.foxdenstudio.foxsuite.foxcore.platform.text.channel.MessageReceiver;
import net.foxdenstudio.foxsuite.foxcore.platform.world.extent.Extent;

import java.nio.file.Path;

public interface World extends Extent, MessageReceiver {

    String getName();

    Path getDirectory();

}
