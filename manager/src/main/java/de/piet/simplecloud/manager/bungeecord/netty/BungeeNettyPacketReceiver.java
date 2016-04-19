package de.piet.simplecloud.manager.bungeecord.netty;

import de.piet.simplecloud.manager.bungeecord.BungeecordManager;
import de.piet.simplecloud.protocol.NettyPacket;
import de.piet.simplecloud.protocol.packets.bungeecord.BungeecordAlivePacket;
import de.piet.simplecloud.protocol.util.PacketReceiver;
import io.netty.channel.Channel;

/**
 * Created by Peter on 19.04.2016.
 */
public class BungeeNettyPacketReceiver implements PacketReceiver {
    @Override
    public void receivePacket( NettyPacket nettyPacket, Channel channel ) {
        if( nettyPacket instanceof BungeecordAlivePacket ) {
            BungeecordAlivePacket bungeecordAlivePacket = ( BungeecordAlivePacket ) nettyPacket;
            synchronized ( BungeecordManager.getBungeecordInstances() ) {
                if( BungeecordManager.getBungeecordInstances().containsKey( channel ) ) {
                    BungeecordManager.getBungeecordInstances().get( channel ).alivePacket( bungeecordAlivePacket );
                } else {
                    System.out.println( "Received alive packet from unregistered bungeecord!" );
                }
            }
        }
    }
    @Override
    public void channelActive( Channel channel ) {

    }
}
