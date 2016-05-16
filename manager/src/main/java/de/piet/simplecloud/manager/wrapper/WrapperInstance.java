package de.piet.simplecloud.manager.wrapper;

import de.piet.simplecloud.manager.mcserver.MCServerInstance;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Peter on 01.05.2016.
 */
public class WrapperInstance {
    @Getter
    private String name, host;
    @Getter
    private int cpuUsage, averageCpuUsage;
    @Getter
    private List<MCServerInstance> mcServers = Collections.synchronizedList( new ArrayList<>(  ) );
    public WrapperInstance( String name, String host ) {
        this.name = name;
        this.host = host;
    }
    public void startServer( MCServerInstance mcServerInstance ) {

    }
}
