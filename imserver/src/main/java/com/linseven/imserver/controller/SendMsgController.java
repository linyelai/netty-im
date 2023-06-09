package com.linseven.imserver.controller;

import com.linseven.imserver.cache.DataCenter;
import com.linseven.protobuf.IMMessageOuterClass;
import io.netty.channel.Channel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SendMsgController {

    @PostMapping("/sendMsg")
    public boolean sendMsg(@RequestBody IMMessageOuterClass.IMMessage msg){

        String destId = msg.getDestId();

        List<Channel> channelList = DataCenter.getInstance().getChannels(destId);
        if(channelList==null){

            return false;
        }
        for(Channel channel:channelList){
            channel.writeAndFlush(msg);
        }
        return true;


    }
}
