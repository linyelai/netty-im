package com.linseven.repository;

import com.linseven.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository  extends MongoRepository<Message, String> {


}
