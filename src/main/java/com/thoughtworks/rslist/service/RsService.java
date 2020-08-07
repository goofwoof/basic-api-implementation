package com.thoughtworks.rslist.service;


import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.exception.ErrorEmptyObjectException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RsService {
    @Autowired
    private RsEventRepository rsEventRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private VoteService voteService;


    public List<RsEvent> getAllRsEvents() {
        List<RsEvent> rsList = new ArrayList<>();
        rsEventRepository.findAll().forEach(rsEventDto ->
                rsList.add(RsEvent.builder().eventName(rsEventDto.getEventName())
                        .keyWord(rsEventDto.getKeyWord()).build()));
        return rsList;
    }

    public List<RsEvent> getSubRsEvents(Integer start, Integer end) {
        return getAllRsEvents().subList(start,end);
    }

    public List<RsEventDto> getAllRsEventsDto() {
        return (List<RsEventDto>) rsEventRepository.findAll();
    }

    public List<RsEventDto> getSubRsEventsDto(Integer start, Integer end) {
        return getAllRsEventsDto().subList(start,end);
    }

    public RsEventDto getRsEventDtoById(int RsEventId) {
        return rsEventRepository.findById(RsEventId).get();
    }

    public RsEvent getRsEventById(int RsEventId) {
        RsEventDto rsEventDto = getRsEventDtoById(RsEventId);
        return RsEvent.builder().eventName(rsEventDto.getEventName())
                .keyWord(rsEventDto.getKeyWord())
                .id(rsEventDto.getId())
                .voteNum(rsEventDto.getVoteDtoList().stream().mapToInt(item -> item.getVoutNum()).sum())
                        .build();
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
                .keyWord(keyWord).build();
        rsEventRepository.save(rsEventDto);
    }

    public void deleteReEvent(int RsEventId) {
        rsEventRepository.deleteById(RsEventId);
    }
}
