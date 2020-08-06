package com.thoughtworks.rslist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.junit.jupiter.api.*;
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

    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;

    UserDto getUserDto;

    RsEventDto getRsEventDto;
    @BeforeEach
    void setup(){
        userRepository.deleteAll();
        rsEventRepository.deleteAll();
        User user = new User("lili1", "male", 19, "a@a.com", "15029931111");
        UserDto userDto = UserDto.builder().userName(user.getUserName())
                .age(user.getAge()).email(user.getEmail()).gender(user.getGender())
                .phone(user.getPhone()).build();
        userRepository.save(userDto);
        getUserDto = userRepository.findAll().get(0);
        RsEventDto rsEventDto = RsEventDto.builder().eventName("第零条事件").keyWord("无标签").userDto(getUserDto).build();
        rsEventRepository.save(rsEventDto);
        getRsEventDto = rsEventRepository.findAll().get(0);
    }


    @Test
    @Order(0)
    void T0_shoud_return_bad_request_when_add_give_null_param() throws Exception {
        String jsonParam = new ObjectMapper().writeValueAsString(new RsEvent(null, "无标签", getUserDto.getId()));
        mockMvc.perform(post("/rs/add").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid param")));
        jsonParam = new ObjectMapper().writeValueAsString(new RsEvent("第二条事件", null, getUserDto.getId()));
        mockMvc.perform(post("/rs/add").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
        jsonParam = new ObjectMapper().writeValueAsString(new RsEvent("第三条事件", "无标签", getUserDto.getId()));
        mockMvc.perform(post("/rs/add").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(1)
    void T1_add_event() throws Exception {
        String jsonParam = new ObjectMapper().writeValueAsString(new RsEvent("第一条事件", "无标签", getUserDto.getId()));
        mockMvc.perform(post("/rs/add").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
        jsonParam = new ObjectMapper().writeValueAsString(new RsEvent("第二条事件", "无标签", getUserDto.getId()));
        mockMvc.perform(post("/rs/add").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
        jsonParam = new ObjectMapper().writeValueAsString(new RsEvent("第三条事件", "无标签", getUserDto.getId()));
        mockMvc.perform(post("/rs/add").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    @Order(3)
    void T3_get_select_one_event() throws Exception {
        mockMvc.perform(get("/rs/13"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", not(null)))
                .andExpect(jsonPath("$.eventName").value("第零条事件"))
                .andExpect(jsonPath("$.keyWord").value("无标签"))
                .andExpect(jsonPath("$", not(hasKey("userName"))));
        mockMvc.perform(get("/rs/10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid index")));
    }
    
    @Test
    @Order(4)
    void T4_get_list_selected_enents() throws Exception {
        mockMvc.perform(get("/rs/list?start=0&end=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].eventName").value("第一条事件"))
                .andExpect(jsonPath("$[0].keyWord").value("无标签"));

        mockMvc.perform(get("/rs/list?start=10&end=20"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid request param")));
    }
    
    
    @Test
    @Order(5)
    void T5_add_event() throws Exception {
        String jsonParam = new ObjectMapper().writeValueAsString(new RsEvent("猪肉涨价了", "经济", getUserDto.getId()));
        mockMvc.perform(post("/rs/add").content(jsonParam).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/rs/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @Order(6)
    void T6_chang_event() throws Exception {
        mockMvc.perform(post("/rs/change/0")
                .param("eventName", "特朗普辞职了")
                .param("keyWord", "时政")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/rs/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName").value("特朗普辞职了"))
                .andExpect(jsonPath("$.keyWord").value("时政"));
    }

    @Test
    @Order(7)
    void T7_delete_event() throws Exception {
        mockMvc.perform(delete("/rs/delete/"+getRsEventDto.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/"+getRsEventDto.getId()))
                .andExpect(status().isBadRequest());
    }


}
