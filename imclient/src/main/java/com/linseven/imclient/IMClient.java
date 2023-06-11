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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/9 16:30
 */

public class IMClient {

    public static void main(String[] args) throws InterruptedException, IOException {



        //start(username,password);
        ArrayBlockingQueue queue = new ArrayBlockingQueue(100000);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,1000,10000, TimeUnit.MILLISECONDS,queue);
        for(int i=0;i<4;i++){
            String account = (1000+i)+"";
            System.out.println("account:"+account);
            threadPoolExecutor.execute(new Account(account));
        }
        Thread.sleep(1000*30*1);
        new Thread(new MsgSender2()).start();
        while(true){
            Thread.sleep(5000);
        }

    }

    public static void start(String username,String password) throws IOException {

        IMServerInfoService imServerInfoService = new IMServerInfoService();
        UserService userService = new UserService();
        String token = userService.login(username,password);
        IMServerInfo imServerInfo = imServerInfoService.getIMServerInfo(token);
        AppContext.getContext().setImServerInfo(imServerInfo);
        AppContext.getContext().setToken(token);
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
            b.handler(new ClientChannelInitializer(username));
            // Start the client.
            ChannelFuture f = b.connect(imServerInfo.getImserverIp(), imServerInfo.getImPort()).sync(); // (5)
            // Wait until the connection is closed.
            if(f.isDone()){
                ConnectService connectService = new ConnectService();
                connectService.connect(username,imServerInfo);
                new Thread(new MsgSender(username)).start();
            }

            //f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();

            workerGroup.shutdownGracefully();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }
}
