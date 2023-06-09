package com.linseven.model;

import lombok.Data;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collation = "message")
public class Message {

    private String id;
    private String sourceId;
    private String destId;
    private String content;
    private String type;
    private int subType;
    private int status;
    private Date sendTime;
}
