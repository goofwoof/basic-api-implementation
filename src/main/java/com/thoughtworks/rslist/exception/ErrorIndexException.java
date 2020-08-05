package com.thoughtworks.rslist.exception;

public class ErrorIndexException extends RuntimeException{
    @Override
    public String getMessage(){
        return "invalid index";
    }
}
