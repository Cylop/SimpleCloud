package de.piet.simplecloud.manager.plugin;

import de.piet.simplecloud.api.event.CloudEvent;
import de.piet.simplecloud.api.event.CloudEventHandler;
import de.piet.simplecloud.api.event.Listener;
import de.piet.simplecloud.api.plugin.CloudCommand;
import de.piet.simplecloud.api.plugin.PluginManager;
import de.piet.simplecloud.api.util.CloudException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Peter on 05.05.2016.
 */
public class CloudPluginManager extends PluginManager {
    private Map<Class<?>,Set<Method>> eventMethods = new HashMap<>(  );
    private Lock eventMethodMapLock = new ReentrantLock(  );

    private Map<String, CloudCommand> commandListeners = new HashMap<>(  );
    private Lock commandListenersLock = new ReentrantLock(  );
    @Override
    public void registerListener( Listener listener ) {
        Map<Class<?>,Set<Method>> listenerMethods = getListenerMethodsOfClass( listener );
        if( listenerMethods.isEmpty() ) return;
        eventMethodMapLock.lock();
        try {
            for ( Class< ? > eventClass : listenerMethods.keySet( ) ) {
                if( !eventMethods.containsKey( eventClass ) ) {
                    eventMethods.put( eventClass, listenerMethods.get( eventClass ) );
                } else {
                    Set<Method> existingMethods = eventMethods.get( eventClass );
                    existingMethods.addAll( listenerMethods.get( eventClass ) );
                }
            }
        } finally {
            eventMethodMapLock.unlock();
        }
    }

    @Override
    public void registerCommand( String commandName, CloudCommand cloudCommand ) {
        commandListenersLock.lock();
        try {
            if( commandListeners.containsKey( commandName.toLowerCase() ) ) {
                throw new CloudException( "A command listener with that commandname is already registered!" );
            }
            commandListeners.put( commandName.toLowerCase(), cloudCommand );
        } catch ( CloudException e ) {
            e.printStackTrace( );
        } finally {
            commandListenersLock.unlock();
        }
    }

    @Override
    public void callEvent( CloudEvent cloudEvent ) {
        eventMethodMapLock.lock();
        try {
            Set<Method> calledEventMethods = eventMethods.get( cloudEvent );
            if( calledEventMethods != null ) {
                for ( Method calledEventMethod : calledEventMethods ) {
                    calledEventMethod.invoke( cloudEvent );
                }
            }
        } catch ( InvocationTargetException e ) {
            e.printStackTrace( );
        } catch ( IllegalAccessException e ) {
            e.printStackTrace( );
        } finally {
            eventMethodMapLock.unlock();
        }
    }

    private Map<Class<?>,Set<Method>> getListenerMethodsOfClass( Object listenerClass ) {
        Map<Class<?>,Set<Method>> classListeners = new HashMap<>(  );
        for( Method method : listenerClass.getClass().getMethods() ) {
            CloudEventHandler cloudEventHandler = method.getAnnotation( CloudEventHandler.class );
            if( cloudEventHandler != null ) {
                Class<?>[] methodParams = method.getParameterTypes();
                if( methodParams.length == 1 ) {
                    Set<Method> eventMethods = classListeners.get( methodParams[0] );
                    if( eventMethods == null ) {
                        eventMethods = new HashSet<>(  );
                    }
                    eventMethods.add( method );
                    classListeners.put( methodParams[0], eventMethods );
                }
            }
        }
        return classListeners;
    }
}
