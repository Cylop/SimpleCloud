package de.piet.simplecloud.api.bungee;

import de.piet.simplecloud.api.player.CloudPlayer;

import java.util.List;

/**
 * Created by Peter on 05.05.2016.
 */
public abstract class CloudBungee {
    public abstract String getHost();

    public abstract int getPlayerCount();

    public abstract List<CloudPlayer> getCloudPlayers();
}
