package de.piet.simplecloud.protocol.util;

import de.piet.simplecloud.protocol.NettyPacket;
import io.netty.channel.Channel;

/**
 * Created by Peter on 07.04.2016.
 */
public interface NettyHandlerHelper {
    void receivePacket( NettyPacket nettyPacket, Channel channel );
    void addPacketReceiver( NettyHandlerHelper packetReceiver );
    void channelConnected( Channel channel );
    void channelTimeout( Channel channel );
}
