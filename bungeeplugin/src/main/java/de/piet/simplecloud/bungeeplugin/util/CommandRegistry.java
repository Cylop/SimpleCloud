package de.piet.simplecloud.bungeeplugin.util;

import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Peter on 08.05.2016.
 */
public class CommandRegistry {
    private static List<Command> registeredCommands = new ArrayList<>(  );
    private static Lock commandListLock = new ReentrantLock(  );
    public static void receivedBungeeCommandRegisterPacket() {

    }
}
