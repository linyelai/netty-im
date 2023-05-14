package com.linseven.userservice.config;


import com.linseven.IMServerInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/10 13:47
 */
@Configuration
public class RegistryConfig {


    @Bean
    public ServiceDiscovery serviceRegistrar(){

        CuratorFramework client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", new ExponentialBackoffRetry(1000, 3));
        client.start();
        return ServiceDiscoveryBuilder.builder(IMServerInfo.class).client(client).basePath("/imserver").build();

    }
}
