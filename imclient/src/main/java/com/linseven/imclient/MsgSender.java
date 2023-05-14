package com.linseven.imclient;

import com.linseven.protobuf.IMMessageOuterClass;
import io.netty.channel.Channel;

import java.util.Scanner;

public class MsgSender implements Runnable {

    private String username;
    public MsgSender(String username){
        this.username = username;
    }
    @Override
    public void run() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("target username");
        String targetUserId = scanner.nextLine();
        while(true){

            String msg = scanner.nextLine();

           Channel channel =  AppContext.getContext().getChannel();

            IMMessageOuterClass.IMMessage imMessage = IMMessageOuterClass.IMMessage.newBuilder().setType(IMMessageOuterClass.MsgType.text).setDestId(targetUserId).setContent(msg).setSourceId(username).build();

           channel.writeAndFlush(imMessage);
        }
    }
}
