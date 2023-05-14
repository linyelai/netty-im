package com.linseven.imserver.handler;

import com.linseven.protobuf.IMMessageOuterClass;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/9 18:05
 */
public class MsgEncoderHandler extends SimpleChannelInboundHandler<IMMessageOuterClass.IMMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IMMessageOuterClass.IMMessage msg)
            throws Exception {

        // ctx.write(msg);
        ctx.fireChannelActive();

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}