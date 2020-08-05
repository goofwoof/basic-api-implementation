package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RsController {
  @Autowired
  private List<User> userList;
  @Autowired
  private List<RsEvent> rsList;

  private void HandleErrorOfAutowired(){
    userList = userList.stream().filter(User::isInitFunc).collect(Collectors.toList());
    rsList = rsList.stream().filter(RsEvent::isInitFunc).collect(Collectors.toList());
  }

  @GetMapping("/rs/list")
  public ResponseEntity getStringOfreList(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end){
    HandleErrorOfAutowired();
    if(start != null || end != null){
      return ResponseEntity.ok(rsList.subList(start - 1, end));
    }
    return ResponseEntity.ok(rsList);
  }


  @GetMapping("/rs/{index}")
  public ResponseEntity getStringOfSelectedList(@PathVariable int index){
    HandleErrorOfAutowired();
    return ResponseEntity.ok(this.rsList.get(index - 1));
  }

  @PutMapping("/rs/add")
  public void addEvent(@RequestBody @Valid RsEvent rsEvent){
    HandleErrorOfAutowired();
    rsList.add(rsEvent);
    putUserToUserList(rsEvent.getUser());
  }


  private void putUserToUserList(User user){
    if(userList.stream().filter(oneUser-> oneUser.getUserName().equals(user.getUserName())).count() == 0){
      userList.add(user);
    }
  }

  @PostMapping("rs/change/{index}")
  public RsEvent changeEvent(@PathVariable int index, @RequestParam(required = false) String eventName, @RequestParam(required = false) String keyWord){
    HandleErrorOfAutowired();
    if(eventName != null){
      rsList.get(index-1).setEventName(eventName);
    }
    if(keyWord != null){
      rsList.get(index-1).setKeyWord(keyWord);
    }
    return rsList.get(index-1);
  }

  @DeleteMapping("rs/delete/{index}")
  public RsEvent deleteEvent(@PathVariable int index){
    HandleErrorOfAutowired();
    return rsList.remove(index - 1);
  }




}
