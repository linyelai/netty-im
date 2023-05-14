package com.linseven.imclient;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.linseven.IMServerInfo;
import com.linseven.protobuf.IMMessageOuterClass;
import io.netty.channel.Channel;

public class ConnectService {


    public void connect(String userId, IMServerInfo imServerInfo){

        Channel channel =  AppContext.getContext().getChannel();

       String content = new Gson().toJson(imServerInfo);
        IMMessageOuterClass.IMMessage imMessage = IMMessageOuterClass.IMMessage.newBuilder().setContent(content).setSourceId(userId).setType(IMMessageOuterClass.MsgType.connect).build();
        channel.writeAndFlush(imMessage);
    }
}
