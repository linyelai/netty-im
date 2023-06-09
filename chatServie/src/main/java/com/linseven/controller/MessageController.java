package com.linseven.controller;


import com.alibaba.fastjson.JSONObject;
import com.linseven.MessageStatus;
import com.linseven.model.Message;
import com.linseven.protobuf.IMMessageOuterClass;
import com.linseven.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class MessageController {


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private MessageService messageService;
    @PostMapping("/sendUnReadMsg")
    public boolean sendUnReadMsg(@RequestBody Message msg){

      //  message.setStatus(MessageStatus.UNREAD.ordinal());
      //  String messageJson = JSONObject.toJSONString(message);
     //   kafkaTemplate.send("unReadMsgTopic",messageJson);

       log.info("msg:{}",msg);
       return true;
    }




}
