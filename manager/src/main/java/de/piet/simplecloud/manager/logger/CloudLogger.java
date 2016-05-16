package de.piet.simplecloud.manager.logger;

import de.piet.simplecloud.manager.SimpleCloudManager;

import java.io.IOException;
import java.util.logging.*;

/**
 * Created by Peter on 06.05.2016.
 */
public class CloudLogger extends Logger {
    private final Formatter formatter = new ConciseFormatter();
    private final LogDispatcher dispatcher = new LogDispatcher( this );

    @SuppressWarnings(
            {
                    "CallToPrintStackTrace", "CallToThreadStartDuringObjectConstruction"
            })

    public CloudLogger( SimpleCloudManager simpleCloudManager )
    {
        super( "SimpleCloud", null );
        setLevel( Level.ALL );

        try
        {
            FileHandler fileHandler = new FileHandler( "cloud.log", 1 << 24, 8, true );
            fileHandler.setFormatter( formatter );
            addHandler( fileHandler );

            CloudLogWriter consoleHandler = new CloudLogWriter( simpleCloudManager.getConsoleReader() );
            consoleHandler.setLevel( Level.INFO );
            consoleHandler.setFormatter( formatter );
            addHandler( consoleHandler );
        } catch ( IOException ex )
        {
            System.err.println( "Could not register logger!" );
            ex.printStackTrace();
        }
        dispatcher.start();
    }

    @Override
    public void log(LogRecord record)
    {
        dispatcher.queue( record );
    }

    void doLog(LogRecord record)
    {
        super.log( record );
    }
}
