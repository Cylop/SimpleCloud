package de.piet.simplecloud.protocol.bootstrap;

import de.piet.simplecloud.protocol.NettyPacket;
import de.piet.simplecloud.protocol.util.NettyHandlerHelper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Peter on 07.04.2016.
 */
public class NettyHandler extends SimpleChannelInboundHandler<NettyPacket> {
    private NettyHandlerHelper nettyHandlerHelper;
    public NettyHandler( NettyHandlerHelper nettyHandlerHelper ) {
        this.nettyHandlerHelper = nettyHandlerHelper;
    }
    @Override
    protected void channelRead0 ( ChannelHandlerContext channelHandlerContext, NettyPacket nettyPacket ) throws Exception {
        nettyHandlerHelper.receivePacket( nettyPacket, channelHandlerContext.channel() );
    }
    @Override
    public void channelActive ( ChannelHandlerContext ctx ) throws Exception {
        nettyHandlerHelper.channelConnected( ctx.channel() );
    }
    @Override
    public void channelUnregistered( ChannelHandlerContext ctx ) throws Exception {
        nettyHandlerHelper.channelTimeout( ctx.channel() );
    }
}
