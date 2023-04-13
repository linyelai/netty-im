package com.linseven.userservice.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collation = "message")
public class Message {

    private String id;
    private String sourceUserId;
    private String destinationUserId;
    private String msg;
    private String type;
    private int status;
}
