package de.piet.simplecloud.manager.templates;

import lombok.Getter;

/**
 * Created by Peter on 01.05.2016.
 */
public class MCServerTemplate {
    @Getter
    private String name, serverPrefix, startCommand;
    @Getter
    private int minServers, maxServers, lobbyServers;
    public MCServerTemplate( String name, String serverPrefix, String startCommand, int minServers, int maxServers, int lobbyServers ) {
        this.name = name;
        this.serverPrefix = serverPrefix;
        this.startCommand = startCommand;
        this.minServers = minServers;
        this.maxServers = maxServers;
        this.lobbyServers = lobbyServers;
    }
}
