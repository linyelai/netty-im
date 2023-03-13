package com.linseven.userservice.exception;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/13 10:24
 */
public class BusinessException extends  RuntimeException{

    public BusinessException(String msg){
        super(msg);
    }
}
