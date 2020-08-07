package com.thoughtworks.rslist.exception;

public class ErrorEmptyObjectException extends RuntimeException{
    public ErrorEmptyObjectException() {
        super();
    }

    public ErrorEmptyObjectException(String message) {
        super(message);
    }

    public ErrorEmptyObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorEmptyObjectException(Throwable cause) {
        super(cause);
    }
}
