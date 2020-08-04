package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {
  private List<RsEvent> rsList = initalRsEvent();

  private List<RsEvent> initalRsEvent() {
    List<RsEvent> rsList = new ArrayList<>();
    rsList.add(new RsEvent("第一条事件", "无标签"));
    rsList.add(new RsEvent("第二条事件", "无标签"));
    rsList.add(new RsEvent("第三条事件", "无标签"));
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

  @PostMapping("/rs/add")
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

  @GetMapping("rs/delete/{index}")
  public RsEvent deleteEvent(@PathVariable int index){
      return rsList.remove(index - 1);
  }




}
