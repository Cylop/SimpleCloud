package de.piet.simplecloud.bungeeplugin.events;

import de.piet.simplecloud.bungeeplugin.util.BungeeProtocolHelper;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by Peter on 08.05.2016.
 */
public class PreLoginEventListener implements Listener {
    @EventHandler
    public void onPlayerPreLogin( PreLoginEvent preLoginEvent ) {
        if( !BungeeProtocolHelper.isConnected() ) {
            preLoginEvent.setCancelled( true );
            preLoginEvent.setCancelReason( "§cDerzeit besteht ein technisches Problem, versuche es gleich erneut!" );
        }
    }
}
