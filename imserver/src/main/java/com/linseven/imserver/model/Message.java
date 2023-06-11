package com.linseven.imserver.model;

import com.linseven.protobuf.IMMessageOuterClass;
import lombok.Data;

import java.util.Date;

@Data
public class Message {

    private String id;
    private String sourceId;
    private String destId;
    private String content;
    private IMMessageOuterClass.MsgType type;
    private int subType;
    private int status;
    private Date sendTime;
}
