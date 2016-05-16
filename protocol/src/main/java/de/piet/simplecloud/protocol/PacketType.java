package de.piet.simplecloud.protocol;

import de.piet.simplecloud.protocol.packets.bungeecord.BungeecordAlivePacket;
import de.piet.simplecloud.protocol.packets.bungeecord.BungeecordRegisterPacket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Peter on 11.04.2016.
 */
public enum PacketType {
    BUNGEECORD_REGISTER( 1, BungeecordRegisterPacket.class ),
    BUNGEECORD_ALIVE( 2, BungeecordAlivePacket.class );
    private static Map<String,Class<? extends NettyPacket>> packetsByID = new HashMap<>(  );
    private static Map<Class<? extends NettyPacket>,String> packetsByClass = new HashMap<>(  );
    int packetID;
    Class<? extends NettyPacket> packetClass;
    PacketType( int packetID, Class<? extends NettyPacket> packetClass ) {
        this.packetID = packetID;
        this.packetClass = packetClass;
    }

    public int getPacketID ( ) {
        return packetID;
    }
    public Class<? extends NettyPacket> getPacketClass ( ) {
        return packetClass;
    }

    public static void registerPacket( Class<? extends NettyPacket> nettyPacket ) {
        synchronized ( packetsByID ) {
            packetsByID.put( nettyPacket.getClass( ).getName( ), nettyPacket );
        }
        synchronized ( packetsByClass ) {
            packetsByClass.put( nettyPacket, nettyPacket.getClass().getName() );
        }
    }



    public static Class<? extends NettyPacket> getPacketByID( int packetID ) {
        for ( PacketType packetType : values( ) ) {
            if( packetType.getPacketID() == packetID ) {
                return packetType.getPacketClass();
            }
        }
        return null;
    }
    public static int getPacketIDByClass( Class<? extends NettyPacket> packetClass ) {
        for ( PacketType packetType : values( ) ) {
            if( packetType.getPacketClass().equals( packetClass ) ) {
                return packetType.getPacketID();
            }
        }
        return 0;
    }
}
