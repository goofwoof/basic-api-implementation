package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.exception.ErrorInputException;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoteService {
    @Autowired
    private RsService rsService;
    @Autowired
    private UserService userService;
    @Autowired
    private VoteRepository voteRepository;

    @Transactional
    public void voteForEvent(Integer rsEventId, Integer voteNum, Integer userId) {
        RsEventDto rsEventDto = rsService.getRsEventDtoById(rsEventId);
        if(voteNum <= 0){
            throw new ErrorInputException("error voteNum");
        }
        UserDto userDto = userService.checkVote(voteNum, userId);
        userDto.setVoteNum(userDto.getVoteNum()-voteNum);
        userService.updateSeltected(userDto);
        VoteDto voteDto = VoteDto.builder().rsEventDto(rsEventDto).userDtoVote(userDto).voteNum(voteNum).build();
        voteRepository.save(voteDto);
    }

    public List<VoteDto> getVoteDtosWithinTime(String start, String end) {
        return voteRepository.findAllByVoteTimeBetween(Timestamp.valueOf(start), Timestamp.valueOf(end));
    }

    public List<Vote> getVotesWithinTime(String start, String end) {
        return getVoteDtosWithinTime(start, end)
                .stream().map(this::revertVoteDtoToVote)
                .collect(Collectors.toList());
    }

    public List<Vote> getVotesByPage(Integer pageIndex) {
        return getVoteDtosByPage(pageIndex)
                .stream().map(this::revertVoteDtoToVote)
                .collect(Collectors.toList());

    }

    public Vote revertVoteDtoToVote(VoteDto voteDto){
        return Vote.builder().rsEventId(voteDto.getRsEventDto().getId())
                .userId(voteDto.getUserDtoVote().getId())
                .voteNum(voteDto.getVoteNum())
                .voteTime(voteDto.getVoteTime().toString())
                .build();
    }

    public List<VoteDto> getVoteDtosByPage(Integer pageIndex) {
        Pageable pageable = PageRequest.of(pageIndex-1, 6);
        return voteRepository.findAll(pageable);
    }
}
