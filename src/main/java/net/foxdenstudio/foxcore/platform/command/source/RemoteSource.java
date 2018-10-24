package net.foxdenstudio.foxcore.platform.command.source;

import net.foxdenstudio.foxcore.platform.command.CommandSource;
import net.foxdenstudio.foxcore.platform.network.RemoteConnection;

public interface RemoteSource extends CommandSource {

    RemoteConnection getConnection();

}
