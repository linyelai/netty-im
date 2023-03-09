package com.linseven.imserver;

import com.linseven.imserver.handler.ConnectServerHandler;
import com.linseven.imserver.handler.ServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/9 15:59
 */
@Component
public class IMServer {

    private  int port =8082;
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup work = new NioEventLoopGroup();

    @PostConstruct
    public void start() throws Exception {
        System.out.println("启动记载netty");
        ServerBootstrap b = new ServerBootstrap();
        b.group(boss,work)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress("127.0.0.1",port))
                .childHandler(new ServerChannelInitializer());
        System.out.println("启动加载netty2");
        ChannelFuture channelFuturef = b.bind().sync();
        if (channelFuturef.isSuccess()){
            System.out.println("启动成功");
        }


    }

    @PreDestroy
    private void destory() throws Exception{
        boss.shutdownGracefully();
        work.shutdownGracefully();
        System.out.println("关闭server");
    }
}
