package com.linseven.imserver.controller;

import com.linseven.imserver.cache.DataCenter;
import com.linseven.protobuf.IMMessageOuterClass;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/9 15:56
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public String index(){


        return "hello world";
    }
}
