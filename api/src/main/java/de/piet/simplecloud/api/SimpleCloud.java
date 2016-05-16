package de.piet.simplecloud.api;

import de.piet.simplecloud.api.player.CloudPlayer;
import de.piet.simplecloud.api.plugin.PluginManager;
import de.piet.simplecloud.api.server.CloudServer;
import lombok.Getter;

import java.util.List;

/**
 * Created by Peter on 05.05.2016.
 */
public abstract class SimpleCloud {
    @Getter
    private static SimpleCloud instance;
    public static void setInstance( SimpleCloud simpleCloud ) {
        SimpleCloud.instance = simpleCloud;
    }

    public abstract PluginManager getPluginManager();

    public abstract String getVersion();

    public abstract CloudPlayer getPlayer( String name );

    public abstract CloudPlayer getPlayerByUUID( String uuid );

    public abstract CloudServer getServer( String serverName );

    public abstract List<CloudPlayer> getPlayers();
}
