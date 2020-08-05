package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Component
public class RsEvent extends RsEventPrototype {

    @JsonIgnore
    @Valid
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
}
