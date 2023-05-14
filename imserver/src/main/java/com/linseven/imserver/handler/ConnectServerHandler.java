package com.linseven.imserver.handler;

import com.linseven.imserver.cache.DataCenter;
import com.linseven.protobuf.IMMessageOuterClass;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/9 16:01
 */
public class ConnectServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){

        IMMessageOuterClass.IMMessage imMessage = (IMMessageOuterClass.IMMessage)msg;
        System.out.println(imMessage.getContent());
        // 获取目的 channel
        IMMessageOuterClass.MsgType msgType = imMessage.getType();
        String userId = imMessage.getSourceId();
        DataCenter instance = DataCenter.getInstance();
        if(msgType.equals(IMMessageOuterClass.MsgType.connect)){

            instance.addChannel(userId,ctx.channel());
        }else if(msgType.equals(IMMessageOuterClass.MsgType.text)){

            String destId = imMessage.getDestId();
            //
           List<Channel> channelList =  instance.getChannels(destId);
           for(Channel channel:channelList){

               channel.writeAndFlush(imMessage);
           }

        }

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {


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