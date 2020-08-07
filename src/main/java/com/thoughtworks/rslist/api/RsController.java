package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.exception.ErrorIndexException;
import com.thoughtworks.rslist.exception.ErrorInputException;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import com.thoughtworks.rslist.service.RsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class RsController {

  @Autowired
  private RsService rsService;
  @Autowired
  private RsEventRepository rsEventRepository;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private VoteRepository voteRepository;

  @GetMapping("/rss")
  public ResponseEntity getStringOfreList(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end){
    if(start != null || end != null){
      try{
        return ResponseEntity.ok(rsService.getSubRsEvents(start, end));
      }catch(IndexOutOfBoundsException e){
        throw new ErrorInputException("invalid request param");
      }
    }
    return ResponseEntity.ok(rsService.getAllRsEvents());
  }


  @GetMapping("/rs/{index}")
  public ResponseEntity getStringOfSelectedList(@PathVariable int index){
    return ResponseEntity.ok(rsService.getRsEventById(index));
  }

  @PostMapping("/rs")
  public ResponseEntity addEvent(@RequestBody @Valid RsEvent rsEvent){
    rsService.addNewReEvent(rsEvent);
    return ResponseEntity.created(null).build();
  }


  @PatchMapping("rs/{index}")
  public ResponseEntity changeEvent(@PathVariable int index, @RequestParam(required = false) String eventName, @RequestParam(required = false) String keyWord, @RequestParam Integer userId){
    rsService.changeReEvent(index, eventName, keyWord, userId);
    return ResponseEntity.created(null).build();
  }

  @DeleteMapping("rs/{index}")
  public ResponseEntity deleteEvent(@PathVariable int index){
    rsService.deleteReEvent(index);
    return ResponseEntity.ok().build();
  }
}
