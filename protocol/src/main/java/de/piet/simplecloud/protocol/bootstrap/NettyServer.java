package de.piet.simplecloud.protocol.bootstrap;

import de.piet.simplecloud.protocol.NettyPacket;
import de.piet.simplecloud.protocol.util.NettyHandlerHelper;
import de.piet.simplecloud.protocol.util.PipelineUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Peter on 07.04.2016.
 */
public class NettyServer implements Runnable, NettyHandlerHelper {
    private static List<NettyHandlerHelper> packetReceivers = Collections.synchronizedList( new ArrayList<>(  ) );
    private NettyHandlerHelper instance;
    private int port;
    public NettyServer( int port ) {
        this.instance = this;
        this.port = port;
    }
    public void run ( ) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(  );
        EventLoopGroup workerGroup = new NioEventLoopGroup(  );
        try {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group( bossGroup, workerGroup )
                .channel( NioServerSocketChannel.class )
                .childHandler( new ChannelInitializer<SocketChannel>( ) {
                    @Override
                    protected void initChannel ( SocketChannel socketChannel ) throws Exception {
                        PipelineUtil.preparePipeline( instance, socketChannel );
                    }
                } )
                .option( ChannelOption.SO_BACKLOG, 128 )
                .childOption( ChannelOption.SO_KEEPALIVE, true );
            ChannelFuture channelFuture = serverBootstrap.bind( port ).sync();
            System.out.println( "Server started on port " + port );
            channelFuture.channel().closeFuture().sync();
        } catch ( InterruptedException e ) {
            e.printStackTrace( );
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
    @Override
    public void receivePacket ( NettyPacket nettyPacket, Channel channel ) {
        synchronized ( packetReceivers ) {
            for( NettyHandlerHelper packetReceiver : packetReceivers ) {
                packetReceiver.receivePacket( nettyPacket, channel );
            }
        }
    }
    @Override
    public void addPacketReceiver ( NettyHandlerHelper packetReceiver ) {
        synchronized ( packetReceivers ) {
            packetReceivers.add( packetReceiver );
        }
    }
    @Override
    public void channelConnected ( Channel channel ) {
        synchronized ( packetReceivers ) {
            for ( NettyHandlerHelper packetReceiver : packetReceivers ) {
                packetReceiver.channelConnected( channel );
            }
        }
    }

    @Override
    public void channelTimeout( Channel channel ) {

    }
}
