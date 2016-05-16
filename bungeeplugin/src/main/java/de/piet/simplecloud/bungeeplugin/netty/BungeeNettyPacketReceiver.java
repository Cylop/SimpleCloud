package de.piet.simplecloud.bungeeplugin.netty;

import de.piet.simplecloud.bungeeplugin.BungeePlugin;
import de.piet.simplecloud.bungeeplugin.util.LoginManager;
import de.piet.simplecloud.protocol.NettyPacket;
import de.piet.simplecloud.protocol.packets.bungeecord.PlayerJoinResultPacket;
import de.piet.simplecloud.protocol.util.NettyHandlerHelper;
import io.netty.channel.Channel;

/**
 * Created by Peter on 07.05.2016.
 */
public class BungeeNettyPacketReceiver implements NettyHandlerHelper {
    @Override
    public void receivePacket( NettyPacket nettyPacket, Channel channel ) {
        if( nettyPacket instanceof PlayerJoinResultPacket ) {
            PlayerJoinResultPacket playerJoinResultPacket = ( PlayerJoinResultPacket ) nettyPacket;
            LoginManager.receiveResultPacket( playerJoinResultPacket );
        }
    }
    @Override
    public void addPacketReceiver( NettyHandlerHelper packetReceiver ) {

    }
    @Override
    public void channelConnected( Channel channel ) {
        BungeePlugin.setCloudChannel( channel );
    }
    @Override
    public void channelTimeout( Channel channel ) {

    }
}
