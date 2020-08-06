package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEventPrototype;
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

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RsListApplicationTests {
    @Autowired
    MockMvc mockMvc;

    @Test
    @Order(0)
    void T0_shoud_return_bad_request_when_add_give_null_param() throws Exception {
        String jsonParam = new ObjectMapper().writeValueAsString(new RsEventPrototype(null, "无标签", new User("lili1", "male", 19, "a@a.com", "15029931111")));
        mockMvc.perform(post("/rs/add").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid param")));
        jsonParam = new ObjectMapper().writeValueAsString(new RsEventPrototype("第二条事件", null, new User("lili1", "male", 19, "a@a.com", "15029931111")));
        mockMvc.perform(post("/rs/add").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
        jsonParam = new ObjectMapper().writeValueAsString(new RsEventPrototype("第三条事件", "无标签", null));
        mockMvc.perform(post("/rs/add").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
        jsonParam = new ObjectMapper().writeValueAsString(new RsEventPrototype("第四条事件", "无标签", new User(null, "male", 19, "a@a.com", "15029931111")));
        mockMvc.perform(post("/rs/add").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
        jsonParam = new ObjectMapper().writeValueAsString(new RsEventPrototype("第五条事件", "无标签", new User("lili1", "male", 10, "a@a.com", "15029931111")));
        mockMvc.perform(post("/rs/add").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
        jsonParam = new ObjectMapper().writeValueAsString(new RsEventPrototype("第六条事件", "无标签", new User("lili1", "male", 19, "aba.com", "15029931111")));
        mockMvc.perform(post("/rs/add").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(1)
    void T1_add_event() throws Exception {
        String jsonParam = new ObjectMapper().writeValueAsString(new RsEventPrototype("第一条事件", "无标签", new User("lili1", "male", 19, "a@a.com", "15029931111")));
        mockMvc.perform(post("/rs/add").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
        jsonParam = new ObjectMapper().writeValueAsString(new RsEventPrototype("第二条事件", "无标签", new User("lili1", "male", 19, "a@a.com", "15029931111")));
        mockMvc.perform(post("/rs/add").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
        jsonParam = new ObjectMapper().writeValueAsString(new RsEventPrototype("第三条事件", "无标签", new User("lili1", "male", 19, "a@a.com", "15029931111")));
        mockMvc.perform(post("/rs/add").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }



    @Test
    @Order(2)
    void T2_contextLoads() throws Exception {
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].eventName").value("第一条事件"))
                .andExpect(jsonPath("$[0].keyWord").value("无标签"))
                .andExpect(jsonPath("$[0]", not(hasKey("userName"))))
                .andExpect(jsonPath("$[1].eventName").value("第二条事件"))
                .andExpect(jsonPath("$[1].keyWord").value("无标签"))
                .andExpect(jsonPath("$[2].eventName").value("第三条事件"))
                .andExpect(jsonPath("$[2].keyWord").value("无标签"));

    }

    @Test
    @Order(3)
    void T3_get_select_one_event() throws Exception {
        mockMvc.perform(get("/rs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName").value("第一条事件"))
                .andExpect(jsonPath("$.keyWord").value("无标签"))
                .andExpect(jsonPath("$", not(hasKey("userName"))));
        mockMvc.perform(get("/rs/10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid index")));
    }
    
    @Test
    @Order(4)
    void T4_get_list_selected_enents() throws Exception {
        mockMvc.perform(get("/rs/list?start=1&end=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventName").value("第一条事件"))
                .andExpect(jsonPath("$[0].keyWord").value("无标签"))
                .andExpect(jsonPath("$[1].eventName").value("第二条事件"))
                .andExpect(jsonPath("$[1].keyWord").value("无标签"));

        mockMvc.perform(get("/rs/list?start=10&end=20"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid request param")));
    }
    
    
    @Test
    @Order(5)
    void T5_add_event() throws Exception {
        String jsonParam = new ObjectMapper().writeValueAsString(new RsEventPrototype("猪肉涨价了", "经济", new User("lili1", "male", 19, "a@a.com", "15029931111")));
        mockMvc.perform(post("/rs/add").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(header().string("index", "4"));
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    @Order(6)
    void T6_chang_event() throws Exception {
        mockMvc.perform(post("/rs/change/1")
                .param("eventName", "特朗普辞职了")
                .param("keyWord", "时政")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.eventName").value("特朗普辞职了"))
                .andExpect(jsonPath("$.keyWord").value("时政"));
    }

    @Test
    @Order(7)
    void T7_delete_event() throws Exception {
        mockMvc.perform(delete("/rs/delete/1"))
                .andExpect(status().isOk())
                //此处是单个执行的结果
                //.andExpect(jsonPath("$.eventName").value("第一条事件"))
                //.andExpect(jsonPath("$.keyWord").value("无标签"));
                //此处是全部执行的结果
                .andExpect(jsonPath("$.eventName").value("特朗普辞职了"))
                .andExpect(jsonPath("$.keyWord").value("时政"));
    }


}
