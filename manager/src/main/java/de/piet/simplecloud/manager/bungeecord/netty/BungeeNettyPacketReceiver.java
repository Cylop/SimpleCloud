package de.piet.simplecloud.manager.bungeecord.netty;

import de.piet.simplecloud.manager.bungeecord.BungeecordManager;
import de.piet.simplecloud.protocol.NettyPacket;
import de.piet.simplecloud.protocol.packets.bungeecord.BungeecordAlivePacket;
import de.piet.simplecloud.protocol.packets.bungeecord.BungeecordRegisterPacket;
import de.piet.simplecloud.protocol.util.NettyHandlerHelper;
import io.netty.channel.Channel;

/**
 * Created by Peter on 19.04.2016.
 */
public class BungeeNettyPacketReceiver implements NettyHandlerHelper {
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
        } else if( nettyPacket instanceof BungeecordRegisterPacket ) {
            BungeecordRegisterPacket bungeecordRegisterPacket = ( BungeecordRegisterPacket ) nettyPacket;
            BungeecordManager.registerBungeecord( channel, bungeecordRegisterPacket );
        }
    }

    @Override
    public void addPacketReceiver( NettyHandlerHelper packetReceiver ) {

    }

    @Override
    public void channelConnected( Channel channel ) {

    }

    @Override
    public void channelTimeout( Channel channel ) {

    }
}
