package de.piet.simplecloud.manager.logger;

import jline.console.ConsoleReader;
import org.fusesource.jansi.Ansi;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Peter on 06.05.2016.
 */
public class CloudLogWriter extends Handler {
    private final ConsoleReader console;
    public CloudLogWriter(ConsoleReader console)
    {
        this.console = console;
    }

    public void print(String s)
    {
        try
        {
            console.print( Ansi.ansi().eraseLine( Ansi.Erase.ALL ).toString() + ConsoleReader.RESET_LINE + s + Ansi.ansi().reset().toString() );
            console.drawLine();
            console.flush();
        } catch ( IOException ex )
        {
        }
    }

    @Override
    public void publish(LogRecord record)
    {
        if ( isLoggable( record ) )
        {
            print( getFormatter().format( record ) );
        }
    }

    @Override
    public void flush()
    {
    }

    @Override
    public void close() throws SecurityException
    {
    }
}
