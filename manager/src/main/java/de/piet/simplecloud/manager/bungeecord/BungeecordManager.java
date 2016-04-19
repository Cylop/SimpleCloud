package de.piet.simplecloud.manager.bungeecord;

import de.piet.simplecloud.protocol.packets.bungeecord.BungeecordRegisterPacket;
import io.netty.channel.Channel;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter on 19.04.2016.
 */
public class BungeecordManager {
    @Getter
    private static Map<Channel,BungeecordInstance> bungeecordInstances = Collections.synchronizedMap( new HashMap<>(  ) );
    public static void registerBungeecord( Channel channel, BungeecordRegisterPacket bungeecordRegisterPacket ) {
        synchronized ( bungeecordInstances ) {
            bungeecordInstances.put( channel, new BungeecordInstance( bungeecordRegisterPacket.getHost(), bungeecordRegisterPacket.getPort(), channel ) );
            System.out.println( "Bungeecord " + bungeecordRegisterPacket.getHost() + ":" + bungeecordRegisterPacket.getPort() + " is now registered!" );
        }
    }
}
