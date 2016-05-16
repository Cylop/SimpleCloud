package de.piet.simplecloud.api.plugin;

import de.piet.simplecloud.api.player.CloudPlayer;

/**
 * Created by Peter on 05.05.2016.
 */
public abstract class CloudCommand {
    public abstract void receiveCommand( CloudPlayer sender, String[] args );
}
