package com.web.book.exception;

/**
 * @anthor sily
 * @date 2019/12/5 - 21:52
 */
public class AppointException extends RuntimeException {
    public AppointException(String message) {
        super(message);
    }

    public AppointException(String message,Throwable cause){
        super(message,cause);
    }
}
