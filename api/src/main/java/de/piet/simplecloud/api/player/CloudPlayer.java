package de.piet.simplecloud.api.player;

import de.piet.simplecloud.api.bungee.CloudBungee;
import de.piet.simplecloud.api.server.CloudServer;

/**
 * Created by Peter on 05.05.2016.
 */
public abstract class CloudPlayer {
    public abstract String getName();

    public abstract String getUUID();

    public abstract String getIP();

    public abstract long getJoinTime();

    public abstract CloudServer getCloudServer();

    public abstract CloudBungee getCloudBungee();

    public abstract void sendToServer( String serverName );

    public abstract void kickPlayer( String reason );
}
