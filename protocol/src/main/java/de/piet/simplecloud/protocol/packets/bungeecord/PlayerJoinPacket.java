package de.piet.simplecloud.protocol.packets.bungeecord;

import de.piet.simplecloud.protocol.NettyPacket;
import de.piet.simplecloud.protocol.util.StringPacketUtil;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

/**
 * Created by Peter on 07.05.2016.
 */
public class PlayerJoinPacket extends NettyPacket {
    @Getter
    private String name, uuid, ip;
    public PlayerJoinPacket( String name, String uuid, String ip ) {
        this.name = name;
        this.uuid = uuid;
        this.ip = ip;
    }
    @Override
    public void readPacket( ByteBuf byteBuf ) {
        name = StringPacketUtil.readString( byteBuf );
        uuid = StringPacketUtil.readString( byteBuf );
        ip = StringPacketUtil.readString( byteBuf );
    }
    @Override
    public void writePacket( ByteBuf byteBuf ) {
        StringPacketUtil.writeStringToByteBuf( byteBuf, name );
        StringPacketUtil.writeStringToByteBuf( byteBuf, uuid );
        StringPacketUtil.writeStringToByteBuf( byteBuf, ip );
    }
}
