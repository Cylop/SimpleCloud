package de.piet.simplecloud.manager.bungeecord.netty;

import de.piet.simplecloud.protocol.bootstrap.NettyServer;

/**
 * Created by Peter on 19.04.2016.
 */
public class BungeeNettyServer {
    private static int port = 4401;
    private static NettyServer nettyServer;
    public static void startBungeeNettyServer() {
        new Thread( ( ) -> {
            nettyServer = new NettyServer( port );
            nettyServer.run();
        } ).start();
    }
}
