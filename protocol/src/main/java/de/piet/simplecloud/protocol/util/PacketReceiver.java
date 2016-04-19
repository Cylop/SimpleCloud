package de.piet.simplecloud.protocol.util;

import de.piet.simplecloud.protocol.NettyPacket;
import io.netty.channel.Channel;

/**
 * Created by Peter on 07.04.2016.
 */
public interface PacketReceiver {
    void receivePacket( NettyPacket nettyPacket, Channel channel );
    void channelActive( Channel channel );
}
