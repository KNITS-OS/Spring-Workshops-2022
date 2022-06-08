package com.knits.product.exception;

public class SystemException extends AppException{

    public SystemException(String message, int code){
        super(message);
        setCode(code);
    }

    public SystemException(Exception e){
        super(e);
    }
}
