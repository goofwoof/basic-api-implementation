package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(0)
    public void should_register_user() throws Exception {
        User user = new User("lili", "male", 19, "a@a.com", "15029931111");
        String jsonParam = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }


    @Test
    public void should_error_when_new_object_give_user_name_more_than_8() throws Exception {
        User user = new User("lilililil", "male", 19, "a@a.com", "15029931111");
        String jsonParam = new ObjectMapper().writeValueAsString(user);
        mockMvc.perform(post("/user").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid user")));
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

    @Test
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

    @Test
    public void should_return_all_users_when_getUser() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userName").value("lili"));
        mockMvc.perform(get("/get/user/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("lili"));
    }

    @Test
    public void should_return_empty_when_delete() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
        mockMvc.perform(delete("/user/delete/1"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}