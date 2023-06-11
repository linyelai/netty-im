package com.linseven.imserver.handler;

import com.google.gson.Gson;
import com.linseven.IMServerInfo;
import com.linseven.imserver.cache.DataCenter;
import com.linseven.imserver.enums.ClientStatus;
import com.linseven.imserver.utils.RedisUtil;
import com.linseven.protobuf.IMMessageOuterClass;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/9 16:01
 */

@ChannelHandler.Sharable
public class ConnectServerHandler extends ChannelInboundHandlerAdapter {

 public    ConnectServerHandler(RedisUtil redisUtil){
        this.redisUtil = redisUtil;
    }

    private RedisUtil redisUtil;
    private static volatile AtomicInteger count = new AtomicInteger();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){

        int i =  count.getAndIncrement();
        System.out.println("count:"+i);
        if(!(msg instanceof IMMessageOuterClass.IMMessage) ){
            ctx.fireChannelRead(msg);
            return;
        }
        IMMessageOuterClass.IMMessage imMessage = (IMMessageOuterClass.IMMessage)msg;
        System.out.println(imMessage.getContent());
        // 获取目的 channel
        IMMessageOuterClass.MsgType msgType = imMessage.getType();
        String userId = imMessage.getSourceId();
        DataCenter instance = DataCenter.getInstance();
        if(msgType.equals(IMMessageOuterClass.MsgType.connect)){

            instance.addChannel(userId,ctx.channel());
            // update status
            redisUtil.set(userId+":status",ClientStatus.Active);
            String content = imMessage.getContent();
            IMServerInfo imServerInfo = new Gson().fromJson(content,IMServerInfo.class);
            redisUtil.set(userId+":imServerInfo",imServerInfo);
        }else if(msgType.equals(IMMessageOuterClass.MsgType.text)){

            String destId = imMessage.getDestId();
            //
           List<Channel> channelList =  instance.getChannels(destId);
           if(channelList==null||channelList.size()==0){

               return;
           }
           for(Channel channel:channelList){

               channel.writeAndFlush(imMessage);
           }
            IMMessageOuterClass.IMMessage ackMsg = IMMessageOuterClass.IMMessage.newBuilder().setType(IMMessageOuterClass.MsgType.ack).setMsgId(imMessage.getMsgId()).build();
            ctx.channel().writeAndFlush(ackMsg);

        }else{
            ctx.fireChannelRead(msg);
        }



    }




}