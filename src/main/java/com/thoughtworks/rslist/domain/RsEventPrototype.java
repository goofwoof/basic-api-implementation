package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class RsEventPrototype {
    @NotNull
    private String eventName;
    @NotNull
    private String keyWord;
    @NotNull
    @Valid
    private User user;
    private boolean initFunc = true;

    public boolean isInitFunc() {
        return initFunc;
    }

    public RsEventPrototype(String eventName, String keyWord, User user) {
        this.eventName = eventName;
        this.keyWord = keyWord;
        this.user = user;
    }

    public RsEventPrototype() {
        initFunc = false;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
