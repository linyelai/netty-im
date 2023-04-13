package com.linseven.userservice.service;

import com.linseven.userservice.MessageRespository;
import com.linseven.userservice.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRespository messageRespository;

    public List<Message> findByUserId(String userId){
        List<Message> messages = new ArrayList<>();

        List<Message> result = messageRespository.findByDestinationUserId(userId);
        messages.addAll(result);
        return messages;
    }
}
