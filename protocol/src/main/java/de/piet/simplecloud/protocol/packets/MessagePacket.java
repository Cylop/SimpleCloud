package de.piet.simplecloud.protocol.packets;

import de.piet.simplecloud.protocol.NettyPacket;
import de.piet.simplecloud.protocol.util.StringPacketUtil;
import io.netty.buffer.ByteBuf;

/**
 * Created by Peter on 11.04.2016.
 */
public class MessagePacket extends NettyPacket {
    private String message;
    public MessagePacket() { }
    public MessagePacket( String message ) {
        this.message = message;
    }
    @Override
    public void readPacket ( ByteBuf byteBuf ) {
        byte[] messageBytes = new byte[ byteBuf.readInt() ];
        byteBuf.readBytes( messageBytes );
        message = StringPacketUtil.getStringFromBytes( messageBytes );
    }
    @Override
    public void writePacket ( ByteBuf byteBuf ) {
        byteBuf.writeInt( message.length() );
        byteBuf.writeBytes( StringPacketUtil.getStringBytes( message ) );
    }
    public String getMessage ( ) {
        return message;
    }
}
