package de.piet.simplecloud.manager.player;

import de.piet.simplecloud.api.bungee.CloudBungee;
import de.piet.simplecloud.api.player.CloudPlayer;
import de.piet.simplecloud.api.server.CloudServer;
import de.piet.simplecloud.manager.bungeecord.BungeecordInstance;
import de.piet.simplecloud.manager.mcserver.MCServerInstance;
import lombok.Getter;

/**
 * Created by Peter on 05.05.2016.
 */
public class MCCloudPlayer extends CloudPlayer {
    @Getter
    private String name, uuid, ip;
    @Getter
    private long joinTime;
    @Getter
    private MCServerInstance mcServerInstance;
    @Getter
    private BungeecordInstance bungeecordInstance;
    @Override
    public String getName( ) {
        return null;
    }

    @Override
    public String getUUID( ) {
        return null;
    }

    @Override
    public String getIP( ) {
        return null;
    }

    @Override
    public long getJoinTime( ) {
        return 0;
    }

    @Override
    public CloudServer getCloudServer( ) {
        return mcServerInstance;
    }

    @Override
    public CloudBungee getCloudBungee( ) {
        return bungeecordInstance;
    }

    @Override
    public void sendToServer( String serverName ) {

    }

    @Override
    public void kickPlayer( String reason ) {

    }
}
