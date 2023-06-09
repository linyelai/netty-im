package com.linseven.imclient;

import com.linseven.IMServerInfo;
import com.linseven.imclient.exception.SendMsgFailedException;
import com.linseven.imclient.service.MessageService;
import com.linseven.imclient.service.UserService;
import com.linseven.protobuf.IMMessageOuterClass;
import io.netty.channel.Channel;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Scanner;

public class MsgSender implements Runnable {

    private String username;
    private static MessageService messageService = new MessageService();
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
            IMMessageOuterClass.IMMessage imMessage = IMMessageOuterClass.IMMessage.newBuilder().setType(IMMessageOuterClass.MsgType.text).setDestId(targetUserId).setContent(msg).setSourceId(username).build();

            try {

                if(friendInfo==null){
                    sendUnReadMsg( imMessage);
                }
                else if (friendInfo.getImserverIp().equals(ownerInfo.getImserverIp()) && friendInfo.getImPort().equals(ownerInfo.getImPort())) {
                    Channel channel = AppContext.getContext().getChannel();
                    channel.writeAndFlush(imMessage);
                    synchronized (AppContext.class) {
                        AppContext.class.wait();
                    }
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
