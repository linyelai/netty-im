package com.linseven.imclient;

import com.linseven.IMServerInfo;
import com.linseven.imclient.service.UserService;
import com.linseven.protobuf.IMMessageOuterClass;
import io.netty.channel.Channel;
import lombok.SneakyThrows;

import java.util.Scanner;

public class MsgSender implements Runnable {

    private String username;
    public MsgSender(String username){
        this.username = username;
    }
    @SneakyThrows
    @Override
    public void run() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("target username");
        String targetUserId = scanner.nextLine();
        // get user whether online
        UserService userService = new UserService() ;
        IMServerInfo friendInfo = userService.getUserOnlineIMServerInfo(targetUserId);
        IMServerInfo ownerInfo = AppContext.getContext().getImServerInfo();

        while(true){

            String msg = scanner.nextLine();
            if(friendInfo.getImserverIp().equals(ownerInfo.getImserverIp())&&friendInfo.getImPort().equals(ownerInfo.getImPort()))
            {
                Channel channel =  AppContext.getContext().getChannel();

                IMMessageOuterClass.IMMessage imMessage = IMMessageOuterClass.IMMessage.newBuilder().setType(IMMessageOuterClass.MsgType.text).setDestId(targetUserId).setContent(msg).setSourceId(username).build();

                channel.writeAndFlush(imMessage);
            }else if(friendInfo!=null){
                //发送到web translate

            }else{
                //发送到离线服务
            }
            synchronized (AppContext.class) {
                AppContext.class.wait();
            }

        }
    }
}
