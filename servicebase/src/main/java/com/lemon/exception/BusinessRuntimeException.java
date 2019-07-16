package com.lemon.exception;

/**
 * description: 业务异常
 *
 * @author lemon
 * @date 2019-07-16 10:32:06 创建
 */
public class BusinessRuntimeException extends RuntimeException{

    static final long serialVersionUID = -7034897190745766728L;

    public BusinessRuntimeException(){
        super();
    }

    public BusinessRuntimeException(String message){
        super(message);
    }

    public BusinessRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessRuntimeException(Throwable cause) {
        super(cause);
    }
}
