package de.piet.simplecloud.api.wrapper;

import de.piet.simplecloud.api.server.CloudServer;

import java.util.List;

/**
 * Created by Peter on 05.05.2016.
 */
public abstract class CloudWrapper {
    public abstract void getHost();

    public abstract List<CloudServer> getServers();

    public abstract double getCpuUsage();

    public abstract double getAverageCPUUsage();

    public abstract double getUsedMemory();

    public abstract double getMaxMemory();
}
