package de.piet.simplecloud.protocol.packets.bungeecord;

import de.piet.simplecloud.protocol.NettyPacket;
import de.piet.simplecloud.protocol.util.StringPacketUtil;
import io.netty.buffer.ByteBuf;
import lombok.Getter;

/**
 * Created by Peter on 08.05.2016.
 */
public class BungeecordRegisterCommandsPacket extends NettyPacket {
    @Getter
    private String[] commandsNames;
    public BungeecordRegisterCommandsPacket( String[] commandsNames ) {
        this.commandsNames = commandsNames;
    }
    @Override
    public void readPacket( ByteBuf byteBuf ) {
        commandsNames = new String[ byteBuf.readInt() ];
        for( int i = 0; i < commandsNames.length; i++ ) {
            commandsNames[ i ] = StringPacketUtil.readString( byteBuf );
        }
    }
    @Override
    public void writePacket( ByteBuf byteBuf ) {
        byteBuf.writeInt( commandsNames.length );
        for( String commandName : commandsNames ) {
            StringPacketUtil.writeStringToByteBuf( byteBuf, commandName );
        }
    }
}
