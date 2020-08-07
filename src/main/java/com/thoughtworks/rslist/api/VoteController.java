package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import com.thoughtworks.rslist.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class VoteController {
    @Autowired
    VoteService voteService;


    @PostMapping("/rs/vote/{rsEventId}")
    @Transactional
    public ResponseEntity addEvent(@PathVariable Integer rsEventId, @RequestParam Integer voteNum, @RequestParam Integer userId){
        voteService.voteForEvent(rsEventId, voteNum, userId);
        return ResponseEntity.created(null).build();
    }

    @PostMapping("/votes/time")
    @Transactional
    public ResponseEntity getVotesWithinTime(@RequestParam String start, String end){
        List<Vote> voteList = voteService.getVotesWithinTime(start, end);
        return ResponseEntity.ok(voteList);
    }

    @PostMapping("/votes")
    @Transactional
    public ResponseEntity getVotesByPage(@RequestParam Integer pageIndex){
        List<Vote> voteList = voteService.getVotesByPage(pageIndex);
        return ResponseEntity.ok(voteList);
    }
}
