package com.linseven.imclient;

import com.linseven.IMServerInfo;
import com.linseven.imclient.handler.ClientChannelInitializer;
import com.linseven.imclient.service.IMServerInfoService;
import com.linseven.imclient.service.UserService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.IOException;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/9 16:30
 */
public class IMClient {

    public static void main(String[] args) throws InterruptedException, IOException {

        String username = args[0];
        String password = args[1];
        for(String arg :args){
            System.out.println(arg);
        }

        start(username,password);

    }

    public static void start(String username,String password) throws IOException {

        IMServerInfoService imServerInfoService = new IMServerInfoService();
        UserService userService = new UserService();
        String token = userService.login(username,password);
        IMServerInfo imServerInfo = imServerInfoService.getIMServerInfo(token);
        AppContext.getContext().setImServerInfo(imServerInfo);
        AppContext.getContext().setToken(token);
        //读取用户id

        //获取未读信息

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap(); // (1)
            b.group(workerGroup); // (2)
            b.channel(NioSocketChannel.class); // (3)
            b.option(ChannelOption.SO_KEEPALIVE, true); // (4)
            b.handler(new ClientChannelInitializer());
            // Start the client.
            ChannelFuture f = b.connect(imServerInfo.getImserverIp(), imServerInfo.getImPort()).sync(); // (5)
            // Wait until the connection is closed.
            ConnectService connectService = new ConnectService();
            connectService.connect(username,imServerInfo);
            new Thread(new MsgSender(username)).start();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            workerGroup.shutdownGracefully();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }
}
