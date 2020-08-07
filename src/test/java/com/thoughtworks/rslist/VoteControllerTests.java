package com.thoughtworks.rslist;


import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VoteControllerTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private RsEventRepository rsEventRepository;
    @Autowired
    private VoteRepository voteRepository;

    UserDto getUserDto;
    UserDto getUserDto1;
    RsEventDto getRsEventDto;

    @BeforeEach
    void setup(){
        userRepository.deleteAll();
        User user = User.builder().userName("lili").gender("male").age(19).email("a@a.com").phone("15029931111").voteNum(10).build();
        UserDto userDto = UserDto.builder().userName(user.getUserName())
                .age(user.getAge()).email(user.getEmail()).gender(user.getGender())
                .phone(user.getPhone()).voteNum(user.getVoteNum()).build();
        userRepository.save(userDto);
        getUserDto = userRepository.findAll().get(0);

        User user1 = User.builder().userName("lilix").gender("male").age(19).email("a@a.com").phone("15029931111").voteNum(10).build();
        UserDto userDto1 = UserDto.builder().userName(user.getUserName())
                .age(user1.getAge()).email(user1.getEmail()).gender(user1.getGender())
                .phone(user1.getPhone()).voteNum(user1.getVoteNum()).build();
        userRepository.save(userDto1);
        getUserDto1 = userRepository.findAll().get(1);

        RsEventDto rsEventDto = RsEventDto.builder().eventName("第零条事件").keyWord("无标签").userDtoRS(getUserDto).build();
        rsEventRepository.save(rsEventDto);
        getRsEventDto = rsEventRepository.findAll().iterator().next();
    }

    @Test
    public void should_return_ok_when_vote_give_correct_info() throws Exception {
        mockMvc.perform(post("/rs/vote/"+getRsEventDto.getId())
                .param("voteNum", "5")
                .param("userId", String.valueOf(getUserDto1.getId())))
                .andExpect(status().isCreated());
    }

    @Test
    public void should_return_bad_request_when_vote_give_over_vote() throws Exception {
        mockMvc.perform(post("/rs/vote/"+getRsEventDto.getId())
                .param("voteNum", "15")
                .param("userId", String.valueOf(getUserDto1.getId())))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void shoud_return_event_with_votnum_when_get_event() throws Exception {
        mockMvc.perform(post("/rs/vote/"+getRsEventDto.getId())
                .param("voteNum", "5")
                .param("userId", String.valueOf(getUserDto1.getId())))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/rs/"+getRsEventDto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eventName").value("第零条事件"))
                .andExpect(jsonPath("$.keyWord").value("无标签"))
                .andExpect(jsonPath("$.id").value(getRsEventDto.getId()))
                .andExpect(jsonPath("$.voteNum").value(5));
    }

    @Test
    public void should_return_vote_when_get_vote_give_start_end_time() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String start = df.format(new Date());
        String end = null;
        for(int i = 0; i< 10; i++){
            Thread.sleep(1000);
            mockMvc.perform(post("/rs/vote/"+getRsEventDto.getId())
                    .param("voteNum", "1")
                    .param("userId", String.valueOf(getUserDto1.getId())))
                    .andExpect(status().isCreated());
            if(i == 6){
                Thread.sleep(1000);
                end = df.format(new Date());
            }
        }
        mockMvc.perform(post("/votes/time")
                .param("start", start)
                .param("end", end))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(7)));
        mockMvc.perform(post("/votes")
                .param("pageIndex", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(6)));
        mockMvc.perform(post("/votes")
                .param("pageIndex", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }
}
