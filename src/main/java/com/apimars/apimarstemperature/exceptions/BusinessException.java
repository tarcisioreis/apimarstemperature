package com.apimars.apimarstemperature.exceptions;

public class BusinessException extends RuntimeException  {

    public BusinessException() {}

    public BusinessException(String message) {
        super(message);
    }

}
