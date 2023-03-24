package com.linseven.config;

import lombok.extern.slf4j.Slf4j;
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

    @KafkaListener(topics = "unReadMsgTopic" )
    public void consume(String message) {
        log.info("接收到消息：" + message);

    }
}
