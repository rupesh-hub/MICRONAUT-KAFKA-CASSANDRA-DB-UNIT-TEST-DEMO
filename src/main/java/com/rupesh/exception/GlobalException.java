package com.rupesh.exception;

public class GlobalException extends RuntimeException {

    public GlobalException(String message, Throwable cause) {
        super(message, cause);
    }
    public GlobalException(String message) {
        super(message);
    }



}
