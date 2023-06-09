package com.linseven.imclient.request;

import com.linseven.protobuf.IMMessageOuterClass;
import lombok.Data;

@Data
public class UnReadMsgRequest {

    private String msgId ;
    private String sourceId;
    private String  destId;
    private  String content;
    IMMessageOuterClass.MsgType type;
    private int  subType ;
    private long  sendTime;
}
