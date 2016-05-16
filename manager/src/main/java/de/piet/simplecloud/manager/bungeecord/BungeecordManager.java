package de.piet.simplecloud.manager.bungeecord;

import de.piet.simplecloud.manager.mcserver.MCServerInstance;
import de.piet.simplecloud.manager.mcserver.MCServerManager;
import de.piet.simplecloud.protocol.packets.bungeecord.BungeecordRegisterPacket;
import io.netty.channel.Channel;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * Created by Peter on 19.04.2016.
 */
public class BungeecordManager {
    @Getter
    private static Map<Channel,BungeecordInstance> bungeecordInstances = new HashMap<>(  );
    private static ReadWriteLock instancesLock = new ReentrantReadWriteLock(  );
    public static void registerBungeecord( Channel channel, BungeecordRegisterPacket bungeecordRegisterPacket ) {
        synchronized ( bungeecordInstances ) {
            BungeecordInstance bungeecordInstance = new BungeecordInstance( bungeecordRegisterPacket.getHost(), bungeecordRegisterPacket.getPort(), channel );
            bungeecordInstances.put( channel, bungeecordInstance );
            System.out.println( "Bungeecord " + bungeecordInstance.getHostName() + " is now registered!" );
        }
    }
    public static void bungeecordTimeout( Channel channel ) {
        instancesLock.readLock().lock();
        if( bungeecordInstances.containsKey( channel ) ) {
            BungeecordInstance bungeecordInstance = bungeecordInstances.get( channel );
            bungeecordInstances.remove( channel );
            instancesLock.readLock().unlock();
            MCServerManager.getServerLock().readLock().lock();
            for( String serverName : MCServerManager.getMcServers().keySet() ) {
                MCServerInstance mcServerInstance = MCServerManager.getMcServers().get( serverName );
                mcServerInstance.bungeecordTimeout( bungeecordInstance );
            }
            MCServerManager.getServerLock().readLock().unlock();
        } else {
            instancesLock.readLock().unlock();
        }
    }
}
