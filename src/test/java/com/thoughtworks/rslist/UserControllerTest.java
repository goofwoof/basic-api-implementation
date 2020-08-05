package com.thoughtworks.rslist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    public void should_register_user() throws Exception {
        User user = new User("lili", "male", 19, "a@a.com", "15029931111");
        String jsonParam = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }


    @Test
    public void should_error_when_new_object_give_user_name_more_than_8() throws Exception {
        User user = new User("lilililil", "male", 19, "a@a.com", "15029931111");
        String jsonParam = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_error_when_new_object_give_user_name_empty() throws Exception {
        User user = new User("", "male", 19, "a@a.com", "15029931111");
        String jsonParam = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_error_when_new_object_give_user_name_null() throws Exception {
        User user = new User(null, "male", 19, "a@a.com", "15029931111");
        String jsonParam = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    public void should_error_when_new_object_give_sge_not_between_18_100() throws Exception {
        User user = new User("lili", "male", 17, "a@a.com", "15029931111");
        String jsonParam = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void should_error_when_new_object_give_unsuitable_email() throws Exception {
        User user = new User("lili", "male", 19, "aa.com", "15029931111");
        String jsonParam = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_error_when_new_object_give_unsuitable_phone() throws Exception {
        User user = new User("lili", "male", 19, "a@a.com", "150299");
        String jsonParam = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

}