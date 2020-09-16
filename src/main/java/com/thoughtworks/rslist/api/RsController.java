package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvent;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class RsController {
    private List<RsEvent> rsList = initRsEvents();

    private List<RsEvent> initRsEvents() {
        List<RsEvent> rsEventList = new ArrayList<>();
        rsEventList.add(new RsEvent("事件1", "无"));
        rsEventList.add(new RsEvent("事件2", "无"));
        rsEventList.add(new RsEvent("事件3", "无"));
        return rsEventList;
    }

    @GetMapping("rs/{index}")
    public RsEvent getOneRsEvent(@PathVariable int index) {
        return rsList.get(index - 1);
    }

    @GetMapping("rs/list")
    public List<RsEvent> getRsEventsBetween(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
        return rsList.subList(start - 1, end);
    }

    @PostMapping("/rs/event")
    public void addRsEvent(@RequestBody RsEvent rsEvent) {
        rsList.add(rsEvent);
    }

    @DeleteMapping("/rs/{deleteIndex}")
    public void deleteRsEvent(@PathVariable int deleteIndex) {
        rsList.remove(deleteIndex - 1);
    }

    @DeleteMapping("rs/list")
    public List<RsEvent> deleteRsEventsBetween(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer end) {
        rsList.removeAll(rsList.subList(start - 1, end));
        return rsList;
    }

    @DeleteMapping("rs/list")
    public List<RsEvent> deleteOneRsEventsBetween(@PathVariable RsEvent uselessRs) {
        Iterator iterator = rsList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().equals(uselessRs)) {
                iterator.remove();
            }
        }
        return rsList;
    }

    @PatchMapping("rs/{modifiedIndex}")
    public List<RsEvent> modifyOneRsEvent(@PathVariable int modifiedIndex, @RequestParam RsEvent newRs) {
        return (List<RsEvent>) rsList.set(modifiedIndex - 1, newRs);
    }

}
