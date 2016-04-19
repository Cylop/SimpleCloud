package de.piet.simplecloud.protocol.util;

import de.piet.simplecloud.protocol.NettyPacket;
import de.piet.simplecloud.protocol.PacketType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Peter on 18.04.2016.
 */
public class PacketEncoder extends MessageToByteEncoder<NettyPacket> {
    @Override
    protected void encode ( ChannelHandlerContext channelHandlerContext, NettyPacket nettyPacket, ByteBuf byteBuf ) throws Exception {
        int packetID = PacketType.getPacketIDByClass( nettyPacket.getClass() );
        if( packetID != 0 ) {
            byteBuf.writeInt( packetID );
            nettyPacket.writePacket( byteBuf );
        }
    }
}
