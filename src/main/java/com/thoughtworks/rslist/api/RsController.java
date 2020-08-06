package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.dto.UserDto;
import com.thoughtworks.rslist.exception.ErrorIndexException;
import com.thoughtworks.rslist.exception.ErrorInputException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RsController {
  @Autowired
  private RsEventRepository rsEventRepository;
  @Autowired
  private UserRepository userRepository;

  @GetMapping("/rs/list")
  public ResponseEntity getStringOfreList(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end){
    List<RsEvent> rsList = new ArrayList<>();
    rsEventRepository.findAll().forEach(rsEventDto ->
      rsList.add(RsEvent.builder().eventName(rsEventDto.getEventName())
      .keyWord(rsEventDto.getKeyWord()).build()));
    if(start != null || end != null){
      try{
        return ResponseEntity.ok(rsList.subList(start, end));
      }catch(IndexOutOfBoundsException e){
        throw new ErrorInputException();
      }

    }
    return ResponseEntity.ok(rsList);
  }


  @GetMapping("/rs/{index}")
  public ResponseEntity getStringOfSelectedList(@PathVariable int index){
    try {
      RsEventDto rsEventDto = rsEventRepository.findById(index).get();
      return ResponseEntity.ok(RsEvent.builder().eventName(rsEventDto.getEventName())
              .keyWord(rsEventDto.getKeyWord()).build());
    }catch (Exception e){
      throw new ErrorIndexException();
    }
  }

  @PostMapping("/rs/add")
  public ResponseEntity addEvent(@RequestBody @Valid RsEvent rsEvent){
    if(!userRepository.findById(rsEvent.getUserId()).isPresent()){
      return ResponseEntity.badRequest().build();
    }
    UserDto userDto = userRepository.findById(rsEvent.getUserId()).get();
    RsEventDto insertData = RsEventDto.builder().eventName(rsEvent.getEventName())
            .keyWord(rsEvent.getKeyWord()).userDtoRS(userDto).build();
    rsEventRepository.save(insertData);
    return ResponseEntity.created(null).build();
  }


  @PatchMapping("rs/change/{index}")
  public ResponseEntity changeEvent(@PathVariable int index, @RequestParam(required = false) String eventName, @RequestParam(required = false) String keyWord, @RequestParam Integer userId){
    if(userId == null || !userRepository.findById(userId).isPresent()){
      return ResponseEntity.badRequest().build();
    }
    RsEventDto rsEventDto = RsEventDto.builder().id(index).eventName(eventName)
            .keyWord(keyWord).build();
    rsEventRepository.save(rsEventDto);
    return ResponseEntity.created(null).build();
  }

  @DeleteMapping("rs/delete/{index}")
  public ResponseEntity deleteEvent(@PathVariable int index){
    rsEventRepository.deleteById(index);
    return ResponseEntity.ok().build();
  }




}
