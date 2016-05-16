package de.piet.simplecloud.manager.mcserver;

import de.piet.simplecloud.api.player.CloudPlayer;
import de.piet.simplecloud.api.server.CloudServer;
import de.piet.simplecloud.api.server.ServerState;
import de.piet.simplecloud.api.wrapper.CloudWrapper;
import de.piet.simplecloud.manager.bungeecord.BungeecordInstance;
import de.piet.simplecloud.manager.templates.MCServerTemplate;
import de.piet.simplecloud.protocol.NettyPacket;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Peter on 01.05.2016.
 */
public class MCServerInstance extends CloudServer {
    @Getter
    private String serverName;
    @Getter
    private int serverPort;
    @Getter
    @Setter
    private Channel channel;
    @Getter
    private MCServerTemplate mcServerTemplate;
    private Map<BungeecordInstance,Integer> onlinePlayers = new HashMap<>(  );
    @Getter
    private int playerCount;
    @Getter
    @Setter
    private ServerState serverState;
    public MCServerInstance( String serverName, int serverPort, MCServerTemplate mcServerTemplate ) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.mcServerTemplate = mcServerTemplate;
    }
    public void receivePlayerCount( BungeecordInstance bungeecordInstance, int playerCount ) {
        synchronized ( onlinePlayers ) {
            onlinePlayers.put( bungeecordInstance, playerCount );
            calculatePlayers();
        }
    }
    public void bungeecordTimeout( BungeecordInstance bungeecordInstance ) {
        synchronized ( onlinePlayers ) {
            if( onlinePlayers.containsKey( bungeecordInstance ) ) {
                onlinePlayers.remove( bungeecordInstance );
                calculatePlayers();
            }
        }
    }
    private void calculatePlayers() {
        int calculatedPlayers = 0;
        for( BungeecordInstance bungeecordInstance : onlinePlayers.keySet() ) {
            calculatedPlayers += onlinePlayers.get( bungeecordInstance );
        }
        playerCount = calculatedPlayers;
    }
    public void sendPacket( NettyPacket nettyPacket ) {
        new Thread( ( ) -> {
            if( channel != null && channel.isActive() ) {
                channel.writeAndFlush( nettyPacket );
            }
        } ).start();
    }
    public boolean isConnected() {
        if( channel != null && channel.isActive() ) {
            return true;
        }

        return false;
    }

    @Override
    public void getName( ) {

    }

    @Override
    public CloudWrapper getWrapper( ) {
        return null;
    }

    @Override
    public List< CloudPlayer > getPlayers( ) {
        return null;
    }

    @Override
    public void stopAndDeleteServer( ) {

    }
}
