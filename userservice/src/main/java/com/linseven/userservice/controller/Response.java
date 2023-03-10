package com.linseven.userservice.controller;

import lombok.Data;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/10 15:21
 */
@Data
public class Response<T> {
    private T data;
    private String errorMsg;
    private int errorCode =0;
}
