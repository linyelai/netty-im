package com.linseven.controller;

import com.codahale.metrics.annotation.Metered;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/24 16:42
 */
@RestController
public class IndexController {

   // @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/")
    //@Metered(name = "request.ls", absolute = true)
    public String index(){

       // kafkaTemplate.send("unReadMsgTopic", "new msg");
        return "chat service";
    }
}
