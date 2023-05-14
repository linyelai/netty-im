package com.linseven.userservice.service;

import com.linseven.IMServerInfo;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class IMServerInfoService {


    @Autowired
    private ServiceDiscovery serviceDiscovery;

    @GetMapping("/getServerInfo")
    public IMServerInfo getServerInfo(String ip,String userId) throws Exception {


        //获取 im server 节点进行连接，首先获取所有节点个数

        List<ServiceInstance> serviceInstances = (List<ServiceInstance>) serviceDiscovery.queryForInstances("/imserver");
       if(serviceInstances==null|| serviceInstances.size()==0){
           return null;
       }
        int index = userId.hashCode()%serviceInstances.size();
        ServiceInstance  serviceInstance = serviceInstances.get(index);
        IMServerInfo  imServerInfo = (IMServerInfo) serviceInstance.getPayload();
        return imServerInfo;
    }
}
