package com.thoughtworks.rslist.exception;

public class ErrorIndexException extends Exception{
    @Override
    public String getMessage(){
        return "invalid index";
    }
}
