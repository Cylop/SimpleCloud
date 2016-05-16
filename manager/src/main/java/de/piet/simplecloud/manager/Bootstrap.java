package de.piet.simplecloud.manager;

import de.piet.simplecloud.manager.lib.LibManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Peter on 19.04.2016.
 */
public class Bootstrap {
    public static void main( String[] args ) {
        // Loading Libs
        LibManager.downloadAndLoadLibs();

        // Load main class of SimpleCloud
        try {
            Class<?> coreClass = ClassLoader.getSystemClassLoader().loadClass( "de.piet.simplecloud.manager.SimpleCloudManager" );
            Constructor constructor = coreClass.getDeclaredConstructor( String[].class );
            constructor.newInstance( new Object[]{ args } );
        } catch ( ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e ) {
            e.printStackTrace();
        }
    }
}
