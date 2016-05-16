package de.piet.simplecloud.api.event.events;

import de.piet.simplecloud.api.event.CloudEvent;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Peter on 07.05.2016.
 */
public class PlayerJoinNetworkEvent extends CloudEvent {
    @Getter
    private String playerName, playerUUID, playerIP;
    @Getter
    @Setter
    private boolean cancelled;
    @Getter
    @Setter
    private String cancelReason;
    public PlayerJoinNetworkEvent( String playerName, String playerUUID, String playerIP ) {
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.playerIP = playerIP;
    }
}
