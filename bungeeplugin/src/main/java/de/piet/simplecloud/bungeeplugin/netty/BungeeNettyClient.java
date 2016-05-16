package de.piet.simplecloud.bungeeplugin.netty;

import de.piet.simplecloud.protocol.bootstrap.NettyClient;

/**
 * Created by Peter on 07.05.2016.
 */
public class BungeeNettyClient {
    public static void startNettyClient( String host, int port ) {
        new Thread( ( ) -> {
            NettyClient nettyClient = new NettyClient( host, port );
            nettyClient.addPacketReceiver( new BungeeNettyPacketReceiver() );
        } ).start();
    }
}
