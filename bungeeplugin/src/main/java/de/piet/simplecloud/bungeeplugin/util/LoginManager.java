package de.piet.simplecloud.bungeeplugin.util;

import de.piet.simplecloud.bungeeplugin.BungeePlugin;
import de.piet.simplecloud.protocol.packets.bungeecord.PlayerJoinResultPacket;
import lombok.Getter;
import net.md_5.bungee.api.event.LoginEvent;
import org.apache.commons.collections.map.CaseInsensitiveMap;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Peter on 07.05.2016.
 */
public class LoginManager {
    private static Map<String,LoginRequest> loginRequestMap = new CaseInsensitiveMap(  );
    private static Lock lock = new ReentrantLock(  );
    public static boolean addEvent( LoginEvent loginEvent ) {
        lock.lock();
        try {
            if( loginRequestMap.containsKey( loginEvent.getConnection().getName() ) ) return false;
            loginRequestMap.put( loginEvent.getConnection().getName(), new LoginRequest( loginEvent.getConnection().getName(), loginEvent, System.currentTimeMillis() + 5000 ) );
            return true;
        } finally {
            lock.unlock();
        }
    }
    public static void receiveResultPacket( PlayerJoinResultPacket playerJoinResultPacket ) {
        lock.lock();
        try {
            if( loginRequestMap.containsKey( playerJoinResultPacket.getName() ) ) {
                LoginRequest loginRequest = loginRequestMap.get( playerJoinResultPacket.getName() );
                loginRequestMap.remove( playerJoinResultPacket.getName() );
                if( !playerJoinResultPacket.isCancelled() ) {
                    loginRequest.getLoginEvent().setCancelled( false );
                }
                loginRequest.getLoginEvent().completeIntent( BungeePlugin.getInstance() );
            }
        } finally {
            lock.unlock();
        }
    }
    public static class LoginRequest {
        @Getter
        private String playerName;
        @Getter
        private LoginEvent loginEvent;
        @Getter
        private long expiration;
        public LoginRequest( String playerName, LoginEvent loginEvent, long expiration ) {
            this.playerName = playerName;
            this.loginEvent = loginEvent;
            this.expiration = expiration;
        }
    }
}
