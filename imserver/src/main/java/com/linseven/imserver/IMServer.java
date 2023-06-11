package com.linseven.imserver;

import com.linseven.IMServerInfo;
import com.linseven.imserver.handler.ServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private ServiceDiscovery serviceDiscovery;
    @Autowired
    private ServerChannelInitializer serverChannelInitializer;
    @Value("${imserver.port}")
    private  int port ;
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup work = new NioEventLoopGroup();

    @Value("${server.port}")
    private int imwebport;

    @PostConstruct
    public void start() throws Exception {
        System.out.println("启动记载netty");
        ServerBootstrap b = new ServerBootstrap();
        b.group(boss,work)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress("127.0.0.1",port))
                .childHandler(serverChannelInitializer);
        System.out.println("启动加载netty2");
        ChannelFuture channelFuturef = b.bind().sync();
        if (channelFuturef.isSuccess()){
            System.out.println("启动成功");
            IMServerInfo imServerInfo = new IMServerInfo();
            imServerInfo.setImPort(port);
            imServerInfo.setImWebPort(imwebport);
            imServerInfo.setImserverIp("127.0.0.1");
            imServerInfo.setImWebServerIP("127.0.0.1");
            ServiceInstance serviceInstance =  ServiceInstance.builder().name("imserver").payload(imServerInfo).build();
            serviceDiscovery.registerService(serviceInstance);

        }


    }

    @PreDestroy
    private void destory() throws Exception{
        boss.shutdownGracefully();
        work.shutdownGracefully();
        System.out.println("关闭server");

    }
}
