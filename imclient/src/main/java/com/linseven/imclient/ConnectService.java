package com.linseven.imclient;

import com.linseven.protobuf.IMMessageOuterClass;
import io.netty.channel.Channel;

public class ConnectService {


    public void connect(String userId){

        Channel channel =  AppContext.getContext().getChannel();

        IMMessageOuterClass.IMMessage imMessage = IMMessageOuterClass.IMMessage.newBuilder().setSourceId(userId).setType(IMMessageOuterClass.MsgType.connect).build();

        channel.writeAndFlush(imMessage);
    }
}
