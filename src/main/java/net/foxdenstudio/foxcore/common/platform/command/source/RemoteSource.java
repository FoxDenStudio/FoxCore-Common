package net.foxdenstudio.foxcore.common.platform.command.source;

import net.foxdenstudio.foxcore.common.platform.command.CommandSource;
import net.foxdenstudio.foxcore.common.platform.network.RemoteConnection;

public interface RemoteSource extends CommandSource {

    RemoteConnection getConnection();

}
