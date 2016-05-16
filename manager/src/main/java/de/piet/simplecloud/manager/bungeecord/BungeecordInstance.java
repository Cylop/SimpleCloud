package de.piet.simplecloud.manager.bungeecord;

import de.piet.simplecloud.api.bungee.CloudBungee;
import de.piet.simplecloud.api.player.CloudPlayer;
import de.piet.simplecloud.protocol.NettyPacket;
import de.piet.simplecloud.protocol.packets.bungeecord.BungeecordAlivePacket;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Peter on 19.04.2016.
 */
public class BungeecordInstance extends CloudBungee {
    private static String host;
    @Getter
    private static int port, onlinePlayers;
    @Getter
    @Setter
    private static Channel channel;
    public BungeecordInstance( String host, int port, Channel channel ) {
        this.host = host;
        this.port = port;
        this.channel = channel;
    }
    public String getHostName() {
        return host + ":" + port;
    }
    public void alivePacket( BungeecordAlivePacket bungeecordAlivePacket ) {
        this.onlinePlayers = bungeecordAlivePacket.getOnlinePlayers();
    }
    public void sendPacket( NettyPacket nettyPacket ) {
        new Thread( ( ) -> {
            if( channel != null && channel.isActive() ) {
                channel.writeAndFlush( nettyPacket );
            }
        } ).start();
    }

    @Override
    public String getHost( ) {
        return host;
    }

    @Override
    public int getPlayerCount( ) {
        return 0;
    }

    @Override
    public List< CloudPlayer > getCloudPlayers( ) {
        return null;
    }
}
