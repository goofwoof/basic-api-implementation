package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.dto.RsEventDto;
import com.thoughtworks.rslist.exception.ErrorIndexException;
import com.thoughtworks.rslist.exception.ErrorInputException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RsController {
  @Autowired
  private RsEventRepository rsEventRepository;
  @Autowired
  private UserRepository userRepository;

  @GetMapping("/rs/list")
  public ResponseEntity getStringOfreList(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end){
    List<RsEventDto> rsList = rsEventRepository.findAll();
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
      return ResponseEntity.ok(rsEventRepository.findById(index).get());
    }catch (Exception e){
      throw new ErrorIndexException();
    }
  }

  @PostMapping("/rs/add")
  public ResponseEntity addEvent(@RequestBody @Valid RsEvent rsEvent){
    if(!userRepository.findById(rsEvent.getUserId()).isPresent()){
      return ResponseEntity.badRequest().build();
    }
    RsEventDto insertData = RsEventDto.builder().eventName(rsEvent.getEventName())
            .keyWord(rsEvent.getKeyWord()).build();
    rsEventRepository.save(insertData);
    return ResponseEntity.created(null).build();
  }


  @PostMapping("rs/change/{index}")
  public ResponseEntity changeEvent(@PathVariable int index, @RequestParam(required = false) String eventName, @RequestParam(required = false) String keyWord){
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
