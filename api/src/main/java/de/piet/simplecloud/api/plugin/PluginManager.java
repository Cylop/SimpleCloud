package de.piet.simplecloud.api.plugin;

import de.piet.simplecloud.api.event.CloudEvent;
import de.piet.simplecloud.api.event.Listener;

/**
 * Created by Peter on 05.05.2016.
 */
public abstract class PluginManager {
    public abstract void registerListener( Listener listener );

    public abstract void registerCommand( String commandName, CloudCommand cloudCommand );

    public abstract void callEvent( CloudEvent cloudEvent );
}
