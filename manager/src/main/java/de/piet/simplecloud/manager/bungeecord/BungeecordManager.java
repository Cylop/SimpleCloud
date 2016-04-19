package de.piet.simplecloud.manager.bungeecord;

import io.netty.channel.Channel;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Peter on 19.04.2016.
 */
public class BungeecordManager {
    @Getter
    private static Map<Channel,BungeecordInstance> bungeecordInstances = Collections.synchronizedMap( new HashMap<>(  ) );
}
