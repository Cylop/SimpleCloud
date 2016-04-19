package de.piet.simplecloud.protocol.bootstrap;

import de.piet.simplecloud.protocol.NettyPacket;
import de.piet.simplecloud.protocol.util.NettyBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Peter on 07.04.2016.
 */
public class NettyHandler extends SimpleChannelInboundHandler<NettyPacket> {
    private NettyBootstrap nettyBootstrap;
    public NettyHandler( NettyBootstrap nettyBootstrap ) {
        this.nettyBootstrap = nettyBootstrap;
    }
    @Override
    protected void channelRead0 ( ChannelHandlerContext channelHandlerContext, NettyPacket nettyPacket ) throws Exception {
        nettyBootstrap.receivePacket( nettyPacket, channelHandlerContext.channel() );
    }
    @Override
    public void channelActive ( ChannelHandlerContext ctx ) throws Exception {
        nettyBootstrap.channelConnected( ctx.channel() );
    }
}
