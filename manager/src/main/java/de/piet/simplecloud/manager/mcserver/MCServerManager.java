package de.piet.simplecloud.manager.mcserver;

import de.piet.simplecloud.api.server.CloudServer;
import lombok.Getter;
import org.apache.commons.collections.map.CaseInsensitiveMap;

import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Peter on 01.05.2016.
 */
public class MCServerManager {
    @Getter
    private static Map<String,MCServerInstance> mcServers = new CaseInsensitiveMap(  );
    @Getter
    private static ReadWriteLock serverLock = new ReentrantReadWriteLock(  );

    public static CloudServer getServer( String serverName ) {
        serverLock.readLock().lock();
        try {
            return mcServers.get( serverName );
        } finally {
            serverLock.readLock().unlock();
        }
    }
}
