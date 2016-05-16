package de.piet.simplecloud.api.util;

/**
 * Created by Peter on 06.05.2016.
 */
public class CloudException extends Exception {
    public CloudException( String errorMessage ) {
        super( errorMessage );
    }
}
