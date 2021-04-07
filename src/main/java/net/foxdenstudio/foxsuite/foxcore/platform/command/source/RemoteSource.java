package net.foxdenstudio.foxsuite.foxcore.platform.command.source;

import net.foxdenstudio.foxsuite.foxcore.platform.network.RemoteConnection;

public interface RemoteSource extends CommandSource {

    RemoteConnection getConnection();

}
