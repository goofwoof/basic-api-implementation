package com.thoughtworks.rslist.exception;

public class ErrorInputException extends RuntimeException {
    public ErrorInputException() {
        super();
    }

    public ErrorInputException(String message) {
        super(message);
    }

    public ErrorInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorInputException(Throwable cause) {
        super(cause);
    }
}
