package com.linseven.imclient;

import com.linseven.IMServerInfo;
import com.linseven.imclient.handler.ClientChannelInitializer;
import com.linseven.imclient.model.UserInfo;
import com.linseven.imclient.service.IMServerInfoService;
import com.linseven.imclient.service.UserService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;

public class Account implements  Runnable {
    private String username;
    public Account(String username){
        this.username = username;
    }

    @Override
    public void run() {

        try {
            start(this.username, "123456");
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    public   void start(String username,String password) throws IOException {


        IMServerInfoService imServerInfoService = new IMServerInfoService();
        UserService userService = new UserService();
        String token = userService.login(username,password);
        IMServerInfo imServerInfo = imServerInfoService.getIMServerInfo(token);
       // AppContext.getContext().setImServerInfo(imServerInfo);
       // AppContext.getContext().setToken(token);
        AppContext.getContext().setImServerInfo(username,imServerInfo);
        AppContext.getContext().setToken(username,token);
        //读取用户id
        UserInfo userInfo = userService.getUserInfo(token);
        //获取未读信息

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        if(imServerInfo==null){
            System.out.println("im server down");
            return;
        }

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ClientChannelInitializer(this.username));
            // Start the client.
            ChannelFuture f = b.connect(imServerInfo.getImserverIp(), imServerInfo.getImPort()).sync(); // (5)
            // Wait until the connection is closed.
            if(f.isSuccess()){
                ConnectService connectService = new ConnectService();
                connectService.connect(this.username,imServerInfo);
              // new Thread( new MsgSender(this.username)).start();
            }

            f.channel().closeFuture().sync();
            System.out.println("finish..");
        } catch (InterruptedException e) {
            e.printStackTrace();

            workerGroup.shutdownGracefully();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            workerGroup.shutdownGracefully();
        }

    }
}
