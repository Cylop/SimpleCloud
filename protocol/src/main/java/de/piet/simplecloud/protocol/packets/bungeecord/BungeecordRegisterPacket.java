package de.piet.simplecloud.protocol.packets.bungeecord;

import de.piet.simplecloud.protocol.NettyPacket;
import de.piet.simplecloud.protocol.util.StringPacketUtil;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

/**
 * Created by Peter on 19.04.2016.
 */
public class BungeecordRegisterPacket extends NettyPacket {
    @Getter
    private String host;
    @Getter
    private int port;
    @Override
    public void readPacket( ByteBuf byteBuf ) {
        this.host = StringPacketUtil.getStringFromBytes( new byte[ byteBuf.readInt() ] );
        this.port = byteBuf.readInt();
    }
    @Override
    public void writePacket( ByteBuf byteBuf ) {
        byteBuf.writeInt( host.length() );
        byteBuf.writeBytes( StringPacketUtil.getStringBytes( this.host ) );
        byteBuf.writeInt( this.port );
    }
}
