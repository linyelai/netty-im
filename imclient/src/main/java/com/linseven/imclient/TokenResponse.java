package com.linseven.imclient;

import lombok.Data;

@Data
public class TokenResponse {

    private String data;
    private String errorMsg;
    private int errorCode =0;
}
