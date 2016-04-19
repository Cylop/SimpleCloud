package de.piet.simplecloud.manager.bungeecord;

import de.piet.simplecloud.protocol.packets.bungeecord.BungeecordAlivePacket;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Peter on 19.04.2016.
 */
public class BungeecordInstance {
    @Getter
    private static String host;
    @Getter
    private static int port, onlinePlayers;
    @Getter
    @Setter
    private static Channel channel;
    private static long timeOutTime = -1;
    public BungeecordInstance( String host, int port, Channel channel ) {
        this.host = host;
        this.port = port;
        this.channel = channel;
    }
    public void alivePacket( BungeecordAlivePacket bungeecordAlivePacket ) {
        timeOutTime = System.currentTimeMillis() + 10000L;
        this.onlinePlayers = bungeecordAlivePacket.getOnlinePlayers();
    }
    public boolean checkPing() {
        if( System.currentTimeMillis() > timeOutTime ) return false;
        return true;
    }
}
