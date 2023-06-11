package com.linseven.imclient;

import com.linseven.IMServerInfo;
import com.linseven.imclient.exception.SendMsgFailedException;
import com.linseven.imclient.service.MessageService;
import com.linseven.imclient.service.UserService;
import com.linseven.protobuf.IMMessageOuterClass;
import io.netty.channel.Channel;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class MsgSender2 implements Runnable {

    private String username;
    private static MessageService messageService = new MessageService();

    @SneakyThrows
    @Override
    public void run() {

        Scanner scanner = new Scanner(System.in);


       // String username = AppContext.getContext().getCurrentUser().getUsername();

        while(true){


            Set<String> nameSet = AppContext.getContext().getUsernameList();
            Iterator<String> iterator = nameSet.iterator();
            while(iterator.hasNext()){
                String username = iterator.next();
                String targetUsername = ((Integer.valueOf(username)+1)%4+1000)+"";
                // get user whether online
                UserService userService = new UserService() ;

                String token = AppContext.getContext().getToken(username);
                IMServerInfo friendInfo = userService.getUserOnlineIMServerInfo(targetUsername,token);
                IMServerInfo ownerInfo = AppContext.getContext().getIMServerInfo(username);

                if(friendInfo==null){
                    friendInfo = userService.getUserOnlineIMServerInfo(targetUsername,token);
                }
                String msg = "hello "+targetUsername+",i am "+username+System.currentTimeMillis();
                IMMessageOuterClass.IMMessage imMessage = IMMessageOuterClass.IMMessage.newBuilder().setType(IMMessageOuterClass.MsgType.text).setDestId(targetUsername).setContent(msg).setSourceId(username).build();
                Channel channel = AppContext.getContext().getChannel(username);
                try {

                    if(friendInfo==null){
                        sendUnReadMsg( imMessage);
                    }
                    else if (friendInfo.getImserverIp().equals(ownerInfo.getImserverIp()) && friendInfo.getImPort().equals(ownerInfo.getImPort())) {

                        channel.writeAndFlush(imMessage);

                    } else if (friendInfo != null) {
                        //发送到web translate
                        String ip = friendInfo.getImWebServerIP();
                        int port = friendInfo.getImWebPort();
                        String sendMsgEnpoint = "http://" + ip + ":" + port + "/sendMsg";
                        try {
                            sendMsg(imMessage, sendMsgEnpoint);
                        } catch (SendMsgFailedException e) {
                            sendUnReadMsg( imMessage);
                        }

                    }



                }catch (SendMsgFailedException e){
                    System.out.println("发送消息失败");
                }
                Thread.sleep(10);

            }
            Thread.sleep(10);


        }
    }

    private void sendUnReadMsg( IMMessageOuterClass.IMMessage imMessage) throws IOException {

        String sendMsgEnpoint = "http://127.0.0.1:3003/sendUnReadMsg";
         sendMsg(imMessage,sendMsgEnpoint);
    }

    private  void sendMsg( IMMessageOuterClass.IMMessage imMessage,String endPoint) throws IOException {

        String sendMsgEnpoint = endPoint;
         messageService.sendMsg(imMessage,sendMsgEnpoint);
    }
}
