package com.linseven.config;

import com.alibaba.fastjson.JSONObject;
import com.linseven.model.Message;
import com.linseven.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/24 17:14
 */
@Configuration
@Slf4j
public class Consumer {

    @Autowired
    private MessageService messageService;

    //@KafkaListener(topics = "unReadMsgTopic" )
    public void consume(String messageJson) {
        log.info("接收到消息：" + messageJson);
        //保存到mongodb
        Message message = JSONObject.parseObject(messageJson,Message.class);
        messageService.addMessage(message);

    }
}
