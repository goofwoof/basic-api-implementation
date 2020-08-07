package com.thoughtworks.rslist.exception;

public class ErrorIndexException extends RuntimeException{
    public ErrorIndexException() {
        super();
    }

    public ErrorIndexException(String message) {
        super(message);
    }

    public ErrorIndexException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorIndexException(Throwable cause) {
        super(cause);
    }
}
