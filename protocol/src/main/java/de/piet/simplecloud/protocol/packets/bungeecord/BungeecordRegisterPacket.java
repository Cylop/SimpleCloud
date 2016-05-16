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
    public BungeecordRegisterPacket() { }
    public BungeecordRegisterPacket( String host, int port ) {
        this.host = host;
        this.port = port;
    }
    @Override
    public void readPacket( ByteBuf byteBuf ) {
        byte[] hostBytes = new byte[ byteBuf.readInt() ];
        byteBuf.readBytes( hostBytes );
        this.host = StringPacketUtil.getStringFromBytes( hostBytes );

        this.port = byteBuf.readInt();
    }
    @Override
    public void writePacket( ByteBuf byteBuf ) {
        byte[] hostBytes = StringPacketUtil.getStringBytes( this.host );
        byteBuf.writeInt( hostBytes.length );
        byteBuf.writeBytes( hostBytes );

        byteBuf.writeInt( this.port );
    }
}
