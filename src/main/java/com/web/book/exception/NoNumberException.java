package com.web.book.exception;

/**
 * @anthor sily
 * @date 2019/12/5 - 21:53
 */
public class NoNumberException extends RuntimeException {
    public NoNumberException(String message) {
        super(message);
    }

    public NoNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}

