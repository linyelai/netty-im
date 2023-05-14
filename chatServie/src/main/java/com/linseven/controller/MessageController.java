package com.linseven.controller;


import com.alibaba.fastjson.JSONObject;
import com.linseven.MessageStatus;
import com.linseven.model.Message;
import com.linseven.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private MessageService messageService;
    @PostMapping("/sendUnReadMsg")
    public void sendUnReadMsg(@RequestBody Message message){

        message.setStatus(MessageStatus.UNREAD.ordinal());
        String messageJson = JSONObject.toJSONString(message);
        kafkaTemplate.send("unReadMsgTopic",messageJson);



    }




}
