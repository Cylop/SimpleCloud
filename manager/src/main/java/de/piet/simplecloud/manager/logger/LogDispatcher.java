package de.piet.simplecloud.manager.logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.LogRecord;

/**
 * Created by Peter on 06.05.2016.
 */
public class LogDispatcher extends Thread {
    private final CloudLogger logger;
    private final BlockingQueue<LogRecord > queue = new LinkedBlockingQueue<>();

    public LogDispatcher( CloudLogger logger)
    {
        super( "SimpleCloud Logger Thread" );
        this.logger = logger;
    }

    @Override
    public void run()
    {
        while ( !isInterrupted() )
        {
            LogRecord record;
            try
            {
                record = queue.take();
            } catch ( InterruptedException ex )
            {
                continue;
            }

            logger.doLog( record );
        }
        for ( LogRecord record : queue )
        {
            logger.doLog( record );
        }
    }

    public void queue(LogRecord record)
    {
        if ( !isInterrupted() )
        {
            queue.add( record );
        }
    }
}
