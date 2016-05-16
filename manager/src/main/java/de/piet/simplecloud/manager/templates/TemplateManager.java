package de.piet.simplecloud.manager.templates;

import de.piet.simplecloud.manager.config.ConfigManager;
import lombok.Getter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Peter on 01.05.2016.
 */
public class TemplateManager {
    private static String templatePath = "config/templates.conf";
    @Getter
    private static List<MCServerTemplate> serverTemplates = Collections.synchronizedList( new ArrayList<>(  ) );
    public static void loadTemplates() {
        if( !ConfigManager.fileExists( templatePath ) ) {
            System.out.println( "Template file does not exists, would be created now" );
            ConfigManager.writeJsonObjectToFile( templatePath, new JSONObject() );
        }
        JSONObject templatesObject = ConfigManager.readJSONObjectFromFile( templatePath );
        int count = 0;
        if( templatesObject != null ) {
            if( templatesObject.containsKey( "templates" ) ) {
                JSONArray jsonArray = ( JSONArray ) templatesObject.get( "templates" );
                for( Object templateObject : jsonArray ) {
                    JSONObject templateJsonObject = ( JSONObject ) templateObject;
                    synchronized ( serverTemplates ) {
                        count++;
                        serverTemplates.add( new MCServerTemplate( ( String ) templateJsonObject.get( "name" ), ( String ) templateJsonObject.get( "prefix" ), ( String ) templateJsonObject.get( "startCommand" ), Integer.valueOf( ( String ) templateJsonObject.get( "minServers" ) ), Integer.valueOf( ( String ) templateJsonObject.get( "maxServers" ) ), Integer.valueOf( ( String ) templateJsonObject.get( "lobbyServers" ) )  ) );
                    }
                }
            }
        }
        System.out.println( "Loaded " + count + " templates!" );
    }
}
