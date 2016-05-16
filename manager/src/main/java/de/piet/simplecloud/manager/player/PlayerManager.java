package de.piet.simplecloud.manager.player;

import lombok.Getter;
import org.apache.commons.collections.map.CaseInsensitiveMap;

import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Peter on 05.05.2016.
 */
public class PlayerManager {
    @Getter
    private static Map<String,MCCloudPlayer> cloudPlayers = new CaseInsensitiveMap(  );
    @Getter
    private static ReadWriteLock playerLock = new ReentrantReadWriteLock(  );
    public static void playerJoin( ) {

    }
    public static MCCloudPlayer getPlayer( String userName ) {
        playerLock.readLock().lock();
        try {
            return cloudPlayers.get( userName );
        } finally {
            playerLock.readLock().unlock();
        }
    }
    public static MCCloudPlayer getPlayerByUUID( String uuid ) {
        playerLock.readLock().lock();
        try {
            for ( String userName : cloudPlayers.keySet( ) ) {
                MCCloudPlayer cloudPlayer = cloudPlayers.get( userName );
                if( cloudPlayer.getUUID().equals( uuid ) ) {
                    return cloudPlayer;
                }
            }
        } finally {
            playerLock.readLock().unlock();
        }
        return null;
    }
}
