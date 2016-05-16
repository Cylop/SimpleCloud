package de.piet.simplecloud.manager.config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Peter on 01.05.2016.
 */
public class ConfigManager {
    private static JSONParser jsonParser = new JSONParser();
    public static JSONObject readJSONObjectFromFile( String path ) {
        try {
            return ( JSONObject ) jsonParser.parse( new FileReader( path ) );
        } catch ( IOException e ) {
            e.printStackTrace( );
        } catch ( ParseException e ) {
            e.printStackTrace( );
        }
        return null;
    }
    public static boolean fileExists( String path ) {
        return new File( path ).exists();
    }
    public static boolean writeJsonObjectToFile( String path, JSONObject jsonObject ) {
        try {
            new FileWriter( path ).write( jsonObject.toJSONString() );
            return true;
        } catch ( IOException e ) {
            e.printStackTrace( );
        }
        return false;
    }
}
