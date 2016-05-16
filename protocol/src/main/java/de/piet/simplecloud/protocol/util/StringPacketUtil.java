package de.piet.simplecloud.protocol.util;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

/**
 * Created by Peter on 11.04.2016.
 */
public class StringPacketUtil {
    private static Charset charset = Charset.forName( "UTF-8" );
    public static byte[] getStringBytes( String string ) {
        return string.getBytes( charset );
    }
    public static String getStringFromBytes( byte[] bytes ) {
        return new String( bytes, charset );
    }
    public static void writeStringToByteBuf( ByteBuf byteBuf, String string ) {
        byte[] stringBytes = string.getBytes( charset );
        byteBuf.writeInt( stringBytes.length );
        byteBuf.writeBytes( stringBytes );
    }
    public static String readString( ByteBuf byteBuf ) {
        byte[] stringBytes = new byte[ byteBuf.readInt() ];
        byteBuf.readBytes( stringBytes );
        return new String( stringBytes, charset );
    }
}
