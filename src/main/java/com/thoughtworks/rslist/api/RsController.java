package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {
  @Autowired
  private List<User> userList;
  private List<RsEvent> rsList = initalRsEvent();

  private List<RsEvent> initalRsEvent() {
    List<RsEvent> rsList = new ArrayList<>();
    User user1 = new User("lili1", "male", 19, "a@a.com", "15029931111");
    User user2 = new User("lili2", "famale", 29, "a@a.com", "15029931111");
    User user3 = new User("lili3", "male", 40, "a@a.com", "15029931111");
    userList.add(user1);
    userList.add(user2);
    userList.add(user3);
    rsList.add(new RsEvent("第一条事件", "无标签", user1));
    rsList.add(new RsEvent("第二条事件", "无标签", user2));
    rsList.add(new RsEvent("第三条事件", "无标签", user3));
    return rsList;
  }

  @GetMapping("/rs/list")
  public List<RsEvent> getStringOfreList(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end){
    if(start != null || end != null){
      return rsList.subList(start - 1, end);
    }
    return this.rsList;
  }


  @GetMapping("/rs/{index}")
  public RsEvent getStringOfSelectedList(@PathVariable int index){
    return this.rsList.get(index - 1);
  }

  @PutMapping("/rs/add")
  public void addEvent(@RequestBody RsEvent rsEvent){
    rsList.add(rsEvent);
  }

  @PostMapping("rs/change/{index}")
  public RsEvent changeEvent(@PathVariable int index, @RequestParam(required = false) String eventName, @RequestParam(required = false) String keyWord){
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
      return rsList.remove(index - 1);
  }




}
