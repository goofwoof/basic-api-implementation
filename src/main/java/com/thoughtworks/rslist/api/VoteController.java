package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class VoteController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private RsEventRepository rsEventRepository;
    @Autowired
    private VoteRepository voteRepository;


    @PostMapping("/rs/vote/{rsEventId}")
    @Transactional
    public ResponseEntity addEvent(@PathVariable Integer rsEventId, @RequestParam Integer voteNum, @RequestParam Integer userId){
        if(!userRepository.findById(userId).isPresent() || voteNum <= 0 || !rsEventRepository.findById(rsEventId).isPresent()){
            return ResponseEntity.badRequest().header("error-message", "error input").build();
        }
        UserDto userDto = userRepository.findById(userId).get();
        if(userDto.getVoteNum() < voteNum){
            return ResponseEntity.badRequest().header("error-message", "over vote").build();
        }
        userDto.setVoteNum(userDto.getVoteNum()-voteNum);
        userRepository.save(userDto);
        RsEventDto rsEventDto = rsEventRepository.findById(rsEventId).get();
        VoteDto voteDto = VoteDto.builder().rsEventDto(rsEventDto).userDtoVote(userDto).voutNum(voteNum).build();
        voteRepository.save(voteDto);
        return ResponseEntity.created(null).build();
    }

    @PostMapping("/votes/time")
    @Transactional
    public ResponseEntity getVotesWithinTime(@RequestParam String start, String end){
        List<Vote> voteList = voteRepository.findAll().stream()
                .filter(voteDto -> voteDto.getVoteTime().getTime() > Timestamp.valueOf(end).getTime() || voteDto.getVoteTime().getTime() < Timestamp.valueOf(start).getTime())
                .map(voteDto -> Vote.builder().rsEventId(voteDto.getRsEventDto().getId()).userId(voteDto.getUserDtoVote().getId())
                    .voteNum(voteDto.getVoutNum()).voteTime(voteDto.getVoteTime().toString()).build()).collect(Collectors.toList());
        return ResponseEntity.ok(voteList);
    }
}
