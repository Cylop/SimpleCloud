package de.piet.simplecloud.manager.lib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Created by Peter on 04.05.2016.
 */
public class LibManager {
    public static void downloadAndLoadLibs() {

        File libFolder = new File( "libs/" );
        if ( !libFolder.exists() && !libFolder.mkdirs() ) {
            System.out.println( "Could not create library Directory" );
            System.exit( -1 );
        }

        checkLibs( libFolder );

        File[] files = libFolder.listFiles();
        if ( files == null ) {
            System.out.println( "Library Directory is corrupted" );
            System.exit( -1 );
        }

        for ( File file : files ) {
            if ( file.getAbsolutePath().endsWith( ".jar" ) ) {
                try {
                    System.out.println( "Loading lib: " + file.getAbsolutePath() );
                    addJARToClasspath( file );
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }


    }
    private static void checkLibs( File libsFolder ) {
        /*
        Source: https://github.com/GoMint/GoMint/blob/master/gomint-server/src/main/java/io/gomint/server/Bootstrap.java#L89
         */
        // Load the dependency list
        try ( BufferedReader reader = new BufferedReader( new FileReader( new File( "libs.dep" ) ) ) ) {
            String libURL;
            while ( ( libURL = reader.readLine() ) != null ) {
                // Check for comment
                if ( libURL.isEmpty() || libURL.equals( System.getProperty( "line.separator" ) ) || libURL.startsWith( "#" ) ) {
                    continue;
                }

                // Head first to get informations about the file
                URL url = new URL( libURL );
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod( "HEAD" );

                // Filter out non java archive content types
                if ( !"application/java-archive".equals( urlConnection.getHeaderField( "Content-Type" ) ) ) {
                    System.out.println( "Skipping the download of " + libURL + " because its not a Java Archive" );
                    continue;
                }

                // We need the contentLength to compare
                int contentLength = Integer.parseInt( urlConnection.getHeaderField( "Content-Length" ) );

                String[] tempSplit = url.getPath().split( "/" );
                String fileName = tempSplit[tempSplit.length - 1];

                // Check if we have a file with the same length
                File libFile = new File( libsFolder, fileName );
                if ( libFile.exists() && libFile.length() == contentLength ) {
                    System.out.println( "Skipping the download of " + libURL + " because there already is a correct sized copy" );
                    continue;
                }

                // Download the file from the Server
                Files.copy( url.openStream(), libFile.toPath(), StandardCopyOption.REPLACE_EXISTING );
                System.out.println( "Downloading library: " + fileName );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    private static void addJARToClasspath( File moduleFile ) throws IOException {
        URL moduleURL = moduleFile.toURI().toURL();
        Class[] parameters = new Class[]{ URL.class };

        ClassLoader sysloader = ClassLoader.getSystemClassLoader();
        Class sysclass = URLClassLoader.class;

        try {
            Method method = sysclass.getDeclaredMethod( "addURL", parameters );
            method.setAccessible( true );
            method.invoke( sysloader, new Object[]{ moduleURL } );
        } catch ( NoSuchMethodException | InvocationTargetException | IllegalAccessException e ) {
            e.printStackTrace();
            throw new IOException( "Error, could not add URL to system classloader" );
        }
    }
}
