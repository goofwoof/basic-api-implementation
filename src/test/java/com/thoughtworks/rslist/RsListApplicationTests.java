package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RsListApplicationTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(1)
    void T1_contextLoads() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].eventName").value("第一条事件"))
                .andExpect(jsonPath("$[0].keyWord").value("无标签"))
                .andExpect(jsonPath("$[1].eventName").value("第二条事件"))
                .andExpect(jsonPath("$[1].keyWord").value("无标签"))
                .andExpect(jsonPath("$[2].eventName").value("第三条事件"))
                .andExpect(jsonPath("$[2].keyWord").value("无标签"));
    }

    @Test
    @Order(2)
    void T2_get_select_one_event() throws Exception {
        mockMvc.perform(get("/rs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName").value("第一条事件"))
                .andExpect(jsonPath("$.keyWord").value("无标签"));
    }
    
    @Test
    @Order(3)
    void T3_get_list_selected_enents() throws Exception {
        mockMvc.perform(get("/rs/list?start=1&end=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventName").value("第一条事件"))
                .andExpect(jsonPath("$[0].keyWord").value("无标签"))
                .andExpect(jsonPath("$[1].eventName").value("第二条事件"))
                .andExpect(jsonPath("$[1].keyWord").value("无标签"));
    }
    
    
    @Test
    @Order(4)
    void T4_add_event() throws Exception {
        String jsonParam = new ObjectMapper().writeValueAsString(new RsEvent("猪肉涨价了", "经济"));
        mockMvc.perform(put("/rs/add").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    @Order(5)
    void T5_chang_event() throws Exception {
        mockMvc.perform(post("/rs/change/1")
                .param("eventName", "特朗普辞职了")
                .param("keyWord", "时政")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName").value("特朗普辞职了"))
                .andExpect(jsonPath("$.keyWord").value("时政"));
    }

    @Test
    void T6_delete_event() throws Exception {
        mockMvc.perform(delete("/rs/delete/1"))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.eventName").value("第一条事件"))
                //.andExpect(jsonPath("$.keyWord").value("无标签"));
                .andExpect(jsonPath("$.eventName").value("特朗普辞职了"))
                .andExpect(jsonPath("$.keyWord").value("时政"));
    }


}
