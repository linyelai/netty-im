package com.linseven.imserver.handler;

import com.linseven.imserver.cache.DataCenter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/9 16:01
 */
public class ConnectServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ctx.fireChannelRead(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        DataCenter.getInstance().addContext(ctx);
        ctx.fireChannelActive();
    }



    @Override
    @SuppressWarnings("deprecation")
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        DataCenter.getInstance().removeContext(ctx);
        ctx.fireExceptionCaught(cause);
    }



}