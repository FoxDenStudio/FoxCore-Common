package net.foxdenstudio.foxcore.common.platform.network;

import java.net.InetSocketAddress;

public interface RemoteConnection {

    InetSocketAddress getAddress();

    InetSocketAddress getVirtualHost();

}
