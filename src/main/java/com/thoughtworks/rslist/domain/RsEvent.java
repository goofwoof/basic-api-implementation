package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

@Component
public class RsEvent extends RsEventPrototype {

    @JsonIgnore
    public User getUser() {
        return super.getUser();
    }

    @JsonProperty
    public void setUser(User user) {
        super.setUser(user);
    }

    public RsEvent(String eventName, String keyWord, User user) {
        super(eventName, keyWord, user);
    }

    public RsEvent() {
        super();
    }

    public String getEventName() {
        return super.getEventName();
    }

    public void setEventName(String eventName) {
        super.setEventName(eventName);
    }

    public String getKeyWord() {
        return super.getKeyWord();
    }

    public void setKeyWord(String keyWord) {
        super.setKeyWord(keyWord);
    }
}
