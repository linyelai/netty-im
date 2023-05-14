package com.linseven.imserver.cache;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

import java.util.*;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/9 17:10
 */
public class DataCenter {

    private final static DataCenter instance = new DataCenter();
    // userId -->key
    private final static Map<String,List<Channel>> contextCache = new HashMap();

    private final static Map<String,String> channelIdMapUserId = new HashMap();

    private DataCenter(){

    }

    public static DataCenter getInstance(){
        return instance;
    }

    public List<Channel> getChannels(String userId){

        return contextCache.get(userId);
    }

    public void addChannel(String userId,Channel channel){


        List<Channel> channels =  contextCache.get(userId);
        if(channels==null){
            channels = new ArrayList<>();
            channels.add(channel);
            contextCache.put(userId,channels);
        }else{
            channels.add(channel);
        }
        channelIdMapUserId.put(channel.id().toString(),userId);

    }


    public String getUserId(ChannelHandlerContext ctx){
        return  channelIdMapUserId.get(ctx.channel().id().toString());
    }

    public void removeContext(ChannelHandlerContext ctx) {

        String userId = channelIdMapUserId.get(ctx.channel().id().toString());
        List<Channel> channels = contextCache.get(userId);
        Iterator<Channel> iterator = channels.iterator();
        while(iterator.hasNext()){
            Channel channel = iterator.next();
            if(channel.id().toString().equals(ctx.channel().id().toString())){
                iterator.remove();
            }
        }
    }
}
