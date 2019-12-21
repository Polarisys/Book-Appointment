package com.web.book.exception;

/**
 * @anthor sily
 * @date 2019/12/5 - 21:54
 */
public class RepeatAppointException extends RuntimeException {
    public RepeatAppointException(String message) {
        super(message);
    }

    public RepeatAppointException(String message, Throwable cause) {
        super(message, cause);
    }
}
