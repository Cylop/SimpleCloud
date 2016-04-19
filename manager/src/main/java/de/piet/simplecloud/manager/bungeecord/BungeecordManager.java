package de.piet.simplecloud.manager.bungeecord;

import de.piet.simplecloud.protocol.packets.bungeecord.BungeecordRegisterPacket;
import io.netty.channel.Channel;
import lombok.Getter;

import java.util.*;

/**
 * Created by Peter on 19.04.2016.
 */
public class BungeecordManager {
    @Getter
    private static Map<Channel,BungeecordInstance> bungeecordInstances = Collections.synchronizedMap( new HashMap<>(  ) );
    public static void startBungeeTimeOutTimer( ) {
        new Timer(  ).scheduleAtFixedRate( new TimerTask( ) {
            @Override
            public void run( ) {
                synchronized ( bungeecordInstances ) {
                    Iterator<Channel> bungeecordIterator = bungeecordInstances.keySet().iterator();
                    while( bungeecordIterator.hasNext() ) {
                        BungeecordInstance bungeecordInstance = bungeecordInstances.get( bungeecordIterator.next() );
                        if( !bungeecordInstance.checkPing() ) {
                            bungeecordIterator.remove();
                            System.out.println( "Bungeecord " + bungeecordInstance.getHostName() + " timed out!" );
                        }
                    }
                }
            }
        }, 5000L, 5000L );
    }
    public static void registerBungeecord( Channel channel, BungeecordRegisterPacket bungeecordRegisterPacket ) {
        synchronized ( bungeecordInstances ) {
            BungeecordInstance bungeecordInstance = new BungeecordInstance( bungeecordRegisterPacket.getHost(), bungeecordRegisterPacket.getPort(), channel );
            bungeecordInstances.put( channel, bungeecordInstance );
            System.out.println( "Bungeecord " + bungeecordInstance.getHostName() + " is now registered!" );
        }
    }
}
