package com.linseven.imclient;

import com.linseven.IMServerInfo;
import com.linseven.imclient.model.UserInfo;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class AppContext {


    private static AppContext context = new AppContext();

    private Channel channel;

    private IMServerInfo imServerInfo;

    private String token;

    private UserInfo currentUser;

    private Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    private Map<String,String> channelIdUsernameMap = new ConcurrentHashMap<>();

    private Map<String,String> tokenMap = new ConcurrentHashMap<>();

    private Map<String,IMServerInfo> imServerInfoMap = new ConcurrentHashMap<>();

    private Map<String,UserInfo> userInfoMap = new ConcurrentHashMap<>();



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




    public void setCurrentUser(String username,UserInfo currentUser) {
       userInfoMap.put(username,currentUser);
    }

    public void setChannel(String username,Channel channel){
        channelMap.put(username,channel);
        channelIdUsernameMap.put(channel.id().toString(),username);
    }
    public Channel getChannel(String username){
        return channelMap.get(username);
    }

    public String  getUsername(Channel channel){
        return channelIdUsernameMap.get(channel.id().toString());
    }

    public void removeChannel(Channel channel){
        String username = channelIdUsernameMap.get(channel.id().toString());
        channelIdUsernameMap.remove(channel.id().toString());
        channelMap.remove(username);
        tokenMap.remove(username);
        imServerInfoMap.remove(username);
    }

    public void setToken(String username,String token){

        tokenMap.put(username,token);
    }

    public String getToken(String username){
        return tokenMap.get(username);
    }

    public void setImServerInfo(String username,IMServerInfo imServerInfo){
        imServerInfoMap.put(username,imServerInfo);
    }

    public IMServerInfo getIMServerInfo(String username){
      return   imServerInfoMap.get(username);
    }



    public Set<String> getUsernameList(){

        return channelMap.keySet();
    }


}
