package net.foxdenstudio.foxsuite.foxcore.platform.network;

import java.net.InetSocketAddress;

public interface RemoteConnection {

    InetSocketAddress getAddress();

    InetSocketAddress getVirtualHost();

}
