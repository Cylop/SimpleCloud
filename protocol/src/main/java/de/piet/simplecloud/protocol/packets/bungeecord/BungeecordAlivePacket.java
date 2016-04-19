package de.piet.simplecloud.protocol.packets.bungeecord;

import de.piet.simplecloud.protocol.NettyPacket;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

/**
 * Created by Peter on 19.04.2016.
 */
public class BungeecordAlivePacket extends NettyPacket {
    @Getter
    private int onlinePlayers;
    public BungeecordAlivePacket() {}
    public BungeecordAlivePacket( int onlinePlayers ) {
        this.onlinePlayers = onlinePlayers;
    }
    public void readPacket( ByteBuf byteBuf ) {
        this.onlinePlayers = byteBuf.readInt();
    }
    public void writePacket( ByteBuf byteBuf ) {
        byteBuf.writeInt( this.onlinePlayers );
    }
}
