package com.linseven.userservice;

import com.linseven.userservice.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRespository extends MongoRepository<Message,String> {



    List<Message> findByDestinationUserId(String destinationUserId);
}
