package com.thoughtworks.rslist.service;


import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.dto.VoteDto;
import com.thoughtworks.rslist.exception.ErrorIndexException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RsService {
    @Autowired
    private RsEventRepository rsEventRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private VoteService voteService;


    public List<RsEvent> getAllRsEvents() {
        return rsEventRepository.findAll().stream()
                .map(this::revertRsEventDtoToRsEvent)
                .collect(Collectors.toList());
    }

    public RsEvent revertRsEventDtoToRsEvent(RsEventDto rsEventDto){
        return RsEvent.builder().eventName(rsEventDto.getEventName())
                .keyWord(rsEventDto.getKeyWord())
                .voteNum(rsEventDto.getVoteDtoList().stream().mapToInt(VoteDto::getVoteNum).sum())
                .userId(rsEventDto.getUserDtoRS().getId())
                .id(rsEventDto.getId())
                .build();
    }

    public List<RsEvent> getSubRsEvents(Integer start, Integer end) {
        return getAllRsEvents().subList(start,end);
    }

    public List<RsEventDto> getAllRsEventsDto() {
        return rsEventRepository.findAll();
    }

    public List<RsEventDto> getSubRsEventsDto(Integer start, Integer end) {
        return getAllRsEventsDto().subList(start,end);
    }

    public RsEventDto getRsEventDtoById(int rsEventId) {
        if(!rsEventRepository.findById(rsEventId).isPresent()){
            throw new ErrorIndexException("invalid index");
        }
        return rsEventRepository.findById(rsEventId).get();
    }

    public RsEvent getRsEventById(int RsEventId) {
        return revertRsEventDtoToRsEvent(getRsEventDtoById(RsEventId));
    }

    public void addNewReEvent(RsEvent rsEvent) {
        UserDto userDto = userService.getUserDtoById(rsEvent.getUserId());
        RsEventDto insertData = RsEventDto.builder().eventName(rsEvent.getEventName())
                .keyWord(rsEvent.getKeyWord()).userDtoRS(userDto).build();
        rsEventRepository.save(insertData);
    }

    public void changeReEvent(int RsEventId, String eventName, String keyWord, int userId) {
        UserDto userDto = userService.getUserDtoById(userId);
        RsEventDto rsEventDto = RsEventDto.builder().id(RsEventId).eventName(eventName)
                .keyWord(keyWord)
                .userDtoRS(userDto).build();
        rsEventRepository.save(rsEventDto);
    }

    public void deleteReEvent(int RsEventId) {
        rsEventRepository.deleteById(RsEventId);
    }
}
