package net.foxdenstudio.foxcore.platform.world;

import net.foxdenstudio.foxcore.platform.text.channel.MessageReceiver;
import net.foxdenstudio.foxcore.platform.world.extent.Extent;

import java.nio.file.Path;

public interface World extends Extent, MessageReceiver {

    String getName();

    Path getDirectory();

}
