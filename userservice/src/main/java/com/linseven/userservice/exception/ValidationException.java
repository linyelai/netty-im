package com.linseven.userservice.exception;

/**
 * @author Tyrion
 * @version 1.0
 * @date 2023/3/10 15:32
 */
public class ValidationException extends  RuntimeException{

    public ValidationException(String msg){
        super(msg);
    }
}
