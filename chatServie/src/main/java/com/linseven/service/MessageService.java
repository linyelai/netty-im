package com.linseven.service;

import com.linseven.model.Message;
import com.linseven.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {


    @Autowired
    private MessageRepository messageRepository;

    public void addMessage(Message message){

          messageRepository.insert(message);
    }
}
