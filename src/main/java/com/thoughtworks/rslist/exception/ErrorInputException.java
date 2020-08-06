package com.thoughtworks.rslist.exception;

public class ErrorInputException extends IndexOutOfBoundsException {
    @Override
    public String getMessage(){
        return "invalid request param";
    }
}
