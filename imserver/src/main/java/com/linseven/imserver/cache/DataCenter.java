package com.linseven.imserver.cache;

import io.netty.channel.ChannelHandlerContext;

import java.util.*;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/9 17:10
 */
public class DataCenter {

    private final static DataCenter instance = new DataCenter();
    private final static Map<String,ChannelHandlerContext> contextCache = new HashMap();

    private DataCenter(){

    }

    public static DataCenter getInstance(){
        return instance;
    }

    public ChannelHandlerContext getContextById(String id){

        return contextCache.get(id);
    }

    public void addContext(ChannelHandlerContext context){

        String id = context.channel().id().toString();
        contextCache.put(id,context);
    }

    public List<ChannelHandlerContext> getAllContext(){

        List<ChannelHandlerContext> allContext = new ArrayList<>();

        Set keySet = contextCache.keySet();
        Iterator<String> iterator = keySet.iterator();
        while(iterator.hasNext()){
            String id = iterator.next();
            allContext.add(contextCache.get(id));
        }
        return allContext;
    }

    public void removeContext(ChannelHandlerContext ctx) {
        String id = ctx.channel().id().toString();
        contextCache.remove(id);
    }
}
