package com.linseven.imserver.controller;

import com.linseven.imserver.model.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMsgController {

    @PostMapping("/sendMsg")
    public void sendMsg(@RequestBody Message msg){

    }
}
