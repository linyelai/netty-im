package com.linseven.imclient.handler;

import com.linseven.imclient.AppContext;
import com.linseven.protobuf.IMMessageOuterClass;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/9 16:54
 */

public class NewMsgHandler extends SimpleChannelInboundHandler<IMMessageOuterClass.IMMessage> {




    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {

        AppContext.getContext().setChannel(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IMMessageOuterClass.IMMessage msg)
            throws Exception {

        System.out.println(msg.getContent());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
