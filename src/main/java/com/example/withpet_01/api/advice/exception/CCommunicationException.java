package com.example.withpet_01.api.advice.exception;

public class CCommunicationException extends RuntimeException {
    public CCommunicationException(String msg, Throwable t) {
        super(msg, t);
    }
    public CCommunicationException(String msg) {
        super(msg);
    }
    public CCommunicationException() {
        super();
    }
}