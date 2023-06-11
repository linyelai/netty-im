package com.linseven.imclient.response;

import lombok.Data;

@Data
public class CommonResult<T> {
    private T data;
    private String errorMsg;
    private int errorCode =0;
}
