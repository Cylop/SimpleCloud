package de.piet.simplecloud.bungeeplugin.events;

import de.piet.simplecloud.bungeeplugin.BungeePlugin;
import de.piet.simplecloud.bungeeplugin.util.BungeeProtocolHelper;
import de.piet.simplecloud.bungeeplugin.util.LoginManager;
import de.piet.simplecloud.protocol.packets.bungeecord.PlayerJoinPacket;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by Peter on 07.05.2016.
 */
public class LoginEventListener implements Listener {
    @EventHandler
    public void onPostLogin( LoginEvent loginEvent ) {
        loginEvent.setCancelled( true );
        loginEvent.setCancelReason( "§cEs ist ein Fehler aufgetreten, versuche es bitte erneut!" );
        loginEvent.registerIntent( BungeePlugin.getInstance() );
        ProxyServer.getInstance().getScheduler().runAsync( BungeePlugin.getInstance( ), ( ) -> {
            if( !LoginManager.addEvent( loginEvent ) ) {
                loginEvent.completeIntent( BungeePlugin.getInstance() );
                return;
            }
            if( !BungeeProtocolHelper.sendPacket( new PlayerJoinPacket( loginEvent.getConnection().getName(), loginEvent.getConnection().getUniqueId().toString(), loginEvent.getConnection().getAddress().getAddress().getHostAddress() ) ) ) {
                loginEvent.completeIntent( BungeePlugin.getInstance() );
            }
        } );
    }
}
