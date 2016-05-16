package de.piet.simplecloud.bungeeplugin.util;

import de.piet.simplecloud.bungeeplugin.BungeePlugin;
import de.piet.simplecloud.protocol.NettyPacket;

/**
 * Created by Peter on 07.05.2016.
 */
public class BungeeProtocolHelper {
    public static boolean sendPacket( NettyPacket nettyPacket ) {
        try {
            if ( isConnected() ) {
                BungeePlugin.getCloudChannel().writeAndFlush( nettyPacket );
                return true;
            }
        } catch ( Exception exc ) {
            exc.printStackTrace();
        }
        return false;
    }
    public static boolean isConnected() {
        try {
            if( BungeePlugin.getCloudChannel() != null && BungeePlugin.getCloudChannel().isActive() ) {
                return true;
            }
        } catch ( Exception exc ) { }
        return false;
    }
}
