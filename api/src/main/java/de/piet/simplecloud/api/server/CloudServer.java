package de.piet.simplecloud.api.server;

import de.piet.simplecloud.api.player.CloudPlayer;
import de.piet.simplecloud.api.wrapper.CloudWrapper;

import java.util.List;

/**
 * Created by Peter on 05.05.2016.
 */
public abstract class CloudServer {
    public abstract void getName();

    public abstract int getPlayerCount();

    public abstract ServerState getServerState();

    public abstract CloudWrapper getWrapper();

    public abstract List<CloudPlayer> getPlayers();

    public abstract void stopAndDeleteServer();
}
