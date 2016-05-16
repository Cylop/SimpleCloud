package de.piet.simplecloud.protocol.packets.bungeecord;

import de.piet.simplecloud.protocol.NettyPacket;
import de.piet.simplecloud.protocol.util.StringPacketUtil;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

/**
 * Created by Peter on 08.05.2016.
 */
public class PlayerJoinResultPacket extends NettyPacket {
    @Getter
    private String name, cancelReason;
    @Getter
    private boolean cancelled;
    public PlayerJoinResultPacket( String name, String cancelReason, boolean cancelled ) {
        this.name = name;
        this.cancelReason = cancelReason;
        this.cancelled = cancelled;
    }
    @Override
    public void readPacket( ByteBuf byteBuf ) {
        name = StringPacketUtil.readString( byteBuf );
        cancelReason = StringPacketUtil.readString( byteBuf );
        cancelled = byteBuf.readBoolean();
    }
    @Override
    public void writePacket( ByteBuf byteBuf ) {
        StringPacketUtil.writeStringToByteBuf( byteBuf, name );
        StringPacketUtil.writeStringToByteBuf( byteBuf, cancelReason );
        byteBuf.writeBoolean( cancelled );
    }
}
