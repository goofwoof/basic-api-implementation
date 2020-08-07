package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class VoteController {
    @Autowired
    VoteService voteService;


    @PostMapping("/vote/{rsEventId}")
    @Transactional
    public ResponseEntity addEvent(@PathVariable Integer rsEventId, @RequestParam Integer voteNum, @RequestParam Integer userId){
        voteService.voteForEvent(rsEventId, voteNum, userId);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/votes/withintime")
    @Transactional
    public ResponseEntity getVotesWithinTime(@RequestParam String start, String end){
        List<Vote> voteList = voteService.getVotesWithinTime(start, end);
        return ResponseEntity.ok(voteList);
    }

    @GetMapping("/votes")
    @Transactional
    public ResponseEntity getVotesByPage(@RequestParam Integer pageIndex){
        List<Vote> voteList = voteService.getVotesByPage(pageIndex);
        return ResponseEntity.ok(voteList);
    }
}
