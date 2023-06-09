package com.linseven.imclient;

import com.linseven.IMServerInfo;
import io.netty.channel.Channel;

public class AppContext {


    private static AppContext context = new AppContext();

    private Channel channel;

    private IMServerInfo imServerInfo;

    private String token;

    public IMServerInfo getImServerInfo() {
        return imServerInfo;
    }

    public void setImServerInfo(IMServerInfo imServerInfo) {
        this.imServerInfo = imServerInfo;
    }

    private AppContext(){

    }

    public static AppContext getContext(){

        return  context;
    }

    public void setChannel(Channel channel){
        this.channel = channel;
    }

    public Channel getChannel(){
        return this.channel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
