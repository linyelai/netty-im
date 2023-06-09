package com.linseven.userservice.controller;

import com.linseven.userservice.model.Message;
import com.linseven.userservice.service.MessageService;
import com.linseven.userservice.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private ThreadLocal<UserVo> threadLocal;

    @GetMapping("/getUnReadMsg")
    public Response getUnReadMsg(){

        Response response = new Response();
        UserVo userVo = threadLocal.get();
        String userId = userVo.getUserId()+"";
        List<Message> messageList = messageService.findByUserId(userId);
        response.setData(messageList);
        return response;
    }
}
