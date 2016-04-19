package de.piet.simplecloud.protocol.bootstrap;

import de.piet.simplecloud.protocol.NettyPacket;
import de.piet.simplecloud.protocol.util.NettyBootstrap;
import de.piet.simplecloud.protocol.util.PacketDecoder;
import de.piet.simplecloud.protocol.util.PacketEncoder;
import de.piet.simplecloud.protocol.util.PacketReceiver;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Peter on 07.04.2016.
 */
public class NettyClient implements Runnable, NettyBootstrap {
    private static List<PacketReceiver> packetReceivers = Collections.synchronizedList( new ArrayList<>(  ) );
    private NettyBootstrap instance;
    private String host;
    private int port;
    private Channel channel;
    public NettyClient( String host, int port ) {
        this.instance = this;
        this.host = host;
        this.port = port;
    }
    @Override
    public void run ( ) {
        EventLoopGroup workerGroup = new NioEventLoopGroup(  );
        try {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group( workerGroup )
                .channel( NioSocketChannel.class )
                .option( ChannelOption.SO_KEEPALIVE, true )
                .handler( new ChannelInitializer<SocketChannel>( ) {
                    @Override
                    protected void initChannel ( SocketChannel socketChannel ) throws Exception {
                        socketChannel.pipeline().addLast( new PacketDecoder() );
                        socketChannel.pipeline().addLast( new PacketEncoder() );
                        socketChannel.pipeline().addLast( new NettyHandler( instance ) );
                    }
                } );
        ChannelFuture channelFuture = bootstrap.connect( host, port );
            this.channel = channelFuture.channel();
            channelFuture.channel().closeFuture().addListener( new GenericFutureListener< Future< ? super Void > >( ) {
                @Override
                public void operationComplete( Future< ? super Void > future ) throws Exception {
                    System.out.println( "Netty connection lost or refused! Reconnect..." );
                    run();
                }
            } );
            channelFuture.channel().closeFuture().sync();
        } catch ( InterruptedException e ) {
            e.printStackTrace( );
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
    public void sendPacket( NettyPacket nettyPacket ) {
        if( channel != null ) {
            new Thread( ( ) -> {
                channel.writeAndFlush( nettyPacket );
            } ).start();
        }
    }

    @Override
    public void receivePacket ( NettyPacket nettyPacket, Channel channel ) {
        synchronized ( packetReceivers ) {
            for( PacketReceiver packetReceiver : packetReceivers ) {
                packetReceiver.receivePacket( nettyPacket, channel );
            }
        }
    }
    @Override
    public void addPacketReceiver ( PacketReceiver packetReceiver ) {
        synchronized ( packetReceivers ) {
            packetReceivers.add( packetReceiver );
        }
    }
    @Override
    public void channelConnected ( Channel channel ) {
        synchronized ( packetReceivers ) {
            for ( PacketReceiver packetReceiver : packetReceivers ) {
                packetReceiver.channelActive( channel );
            }
        }
    }
}
