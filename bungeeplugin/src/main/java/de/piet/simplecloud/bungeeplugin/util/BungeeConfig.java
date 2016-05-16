package de.piet.simplecloud.bungeeplugin.util;

import de.piet.simplecloud.bungeeplugin.BungeePlugin;
import lombok.Getter;
import net.cubespace.Yamler.Config.YamlConfig;

import java.io.File;

/**
 * Created by Peter on 08.05.2016.
 */
public class BungeeConfig extends YamlConfig {
    @Getter
    private String bungeeName = "BungeeName";
    @Getter
    private String cloudHost = "localhost";
    @Getter
    private int cloudPort = 1234;
    public BungeeConfig() {
        CONFIG_FILE = new File( BungeePlugin.getInstance().getDataFolder(), "config.yml" );
        CONFIG_HEADER = new String[] { "SimpleCloud Bungeecord Plugin config file" };
    }
}
