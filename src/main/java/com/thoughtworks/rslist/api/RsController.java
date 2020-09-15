package com.thoughtworks.rslist.api;

import com.thoughtworks.rslist.domain.RsEvents;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class RsController {
    private List<RsEvents> rsList = initRsEvents;

    private List<RsEvents> initRsEvents() {
        List<RsEvents> rsEventsList = new ArrayList<RsEvents>();
        rsEventsList.add("事件1", "无");
        rsEventsList.add("事件2", "无");
        rsEventsList.add("事件3", "无");
    }

    @GetMapping("rs/list")
    public String getRslist() {
        return rsList.toString();
    }

    @GetMapping("rs/{index}")
    public List<RsEvents> getRsEventsBetween(@RequestParam(required = false) int start, @RequestParam(required = false) int end) {
        return rsList.subList(start - 1, end);
    }


}
