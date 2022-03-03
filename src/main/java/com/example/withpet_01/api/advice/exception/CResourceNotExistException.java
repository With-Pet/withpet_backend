package com.example.withpet_01.api.advice.exception;

public class CResourceNotExistException extends RuntimeException {
    public CResourceNotExistException(String msg, Throwable t) {
        super(msg, t);
    }

    public CResourceNotExistException(String msg) {
        super(msg);
    }

    public CResourceNotExistException() {
        super();
    }
}
