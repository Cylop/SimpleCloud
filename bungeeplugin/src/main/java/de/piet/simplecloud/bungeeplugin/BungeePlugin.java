package de.piet.simplecloud.bungeeplugin;

import de.piet.simplecloud.bungeeplugin.netty.BungeeNettyClient;
import de.piet.simplecloud.bungeeplugin.util.BungeeConfig;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Created by Peter on 07.05.2016.
 */
public class BungeePlugin extends Plugin {
    @Getter
    private static BungeePlugin instance;
    @Getter
    @Setter
    private static Channel cloudChannel;
    @Override
    public void onEnable( ) {
        instance = this;
        // Read config
        BungeeConfig bungeeConfig = new BungeeConfig();
        // Start Netty client
        BungeeNettyClient.startNettyClient( bungeeConfig.getCloudHost(), bungeeConfig.getCloudPort() );
    }
}
