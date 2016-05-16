package de.piet.simplecloud.manager;

import de.piet.simplecloud.api.SimpleCloud;
import de.piet.simplecloud.api.player.CloudPlayer;
import de.piet.simplecloud.api.plugin.PluginManager;
import de.piet.simplecloud.api.server.CloudServer;
import de.piet.simplecloud.manager.logger.CloudLogger;
import de.piet.simplecloud.manager.logger.LoggingOutputStream;
import de.piet.simplecloud.manager.mcserver.MCServerManager;
import de.piet.simplecloud.manager.player.PlayerManager;
import de.piet.simplecloud.manager.plugin.CloudPluginManager;
import jline.console.ConsoleReader;
import lombok.Getter;
import org.fusesource.jansi.AnsiConsole;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Peter on 05.05.2016.
 */
public class SimpleCloudManager extends SimpleCloud {
    @Getter
    private ConsoleReader consoleReader;
    @Getter
    private Logger logger;
    @Getter
    private PluginManager pluginManager = new CloudPluginManager();
    public SimpleCloudManager( String[] args ) throws IOException {
        System.setProperty( "library.jansi.version", "SimpleCloud" );
        AnsiConsole.systemInstall();
        consoleReader = new ConsoleReader(  );
        consoleReader.setExpandEvents( false );
        logger = new CloudLogger( this );

        System.setErr( new PrintStream( new LoggingOutputStream( logger, Level.SEVERE ), true ) );
        System.setOut( new PrintStream( new LoggingOutputStream( logger, Level.INFO ), true ) );

        SimpleCloud.setInstance( this );


        String line;
        while ( ( line = getConsoleReader().readLine( ">" ) )  != null )
        {
            System.out.println( line );
        }
    }
    @Override
    public String getVersion( ) {
        return SimpleCloud.class.getPackage().getImplementationVersion();
    }

    @Override
    public CloudPlayer getPlayer( String name ) {
        return PlayerManager.getPlayer( name );
    }

    @Override
    public CloudPlayer getPlayerByUUID( String uuid ) {
        return PlayerManager.getPlayerByUUID( uuid );
    }

    @Override
    public CloudServer getServer( String serverName ) {
        return MCServerManager.getServer( serverName );
    }

    @Override
    public List< CloudPlayer > getPlayers( ) {
        PlayerManager.getPlayerLock().readLock().lock();
        try {
            List<CloudPlayer> cloudPlayerList = new ArrayList<>(  );
            for( String userName : PlayerManager.getCloudPlayers().keySet() ) {
                cloudPlayerList.add( PlayerManager.getCloudPlayers().get( userName ) );
            }
            return cloudPlayerList;
        } finally {
            PlayerManager.getPlayerLock().readLock().unlock();
        }
    }
}
