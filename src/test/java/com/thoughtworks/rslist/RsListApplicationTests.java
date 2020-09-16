package com.thoughtworks.rslist;

import com.thoughtworks.rslist.domain.RsEvent;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RsListApplicationTests {
    @Autowired
    MockMvc mockMvc;


    @Test
    public void should_get_rs_event_list() throws Exception {
        mockMvc.perform(get("/rs/list"))  //controller 那里方法中写int就会报错，必须写Integer无参时才不报错
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].eventName", is("事件1")));
    }

    @Test
    public void should_get_part_of_rs_event_list() throws Exception {
        mockMvc.perform(get("/rs/list?start=1&end=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].eventName", is("事件1")))
                .andExpect(jsonPath("$[0].keyWord", is("无")))
                .andExpect(jsonPath("$[1].eventName", is("事件2")))
                .andExpect(jsonPath("$[1].keyWord", is("无")));
    }

    @Test
    public void should_get_one_rs_event() throws Exception {
        mockMvc.perform(get("/rs/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.eventName", is("事件2")))
                .andExpect(jsonPath("$.keyWord", is("无")));
    }

    @Test
    public void should_add_rs_event() throws Exception {
        String addJson = "{\"eventName\":\"瑞丽偷渡\",\"keyWord\":\"疫情\"}";
        mockMvc.perform(post("/rs/event").content(addJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[3].eventName", is("瑞丽偷渡")))
                .andExpect(jsonPath("$[3].keyWord", is("疫情")));
    }

    @Test
    public void should_delete_some_rs_events() throws Exception {
        mockMvc.perform(delete("/rs/list?start=1&end=2"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].eventName", is("事件3")))
                .andExpect(jsonPath("$[0].keyWord", is("无")));
    }

    @Test
    public void should_delete_one_rs_event() throws Exception {
        mockMvc.perform(delete("/rs/3"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].eventName", is("事件1")))
                .andExpect(jsonPath("$[0].keyWord", is("无")))
                .andExpect(jsonPath("$[1].eventName", is("事件2")))
                .andExpect(jsonPath("$[1].keyWord", is("无")));
    }

    @Test
    public void should_delete_some_list() throws Exception {
        mockMvc.perform(delete("/rs/list?start=1&end=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].eventName", is("事件3")))
                .andExpect(jsonPath("$[0].keyWord", is("无")));
    }

    @Test
    public void should_modify_one_rs_events_by_index() throws Exception {
        String newJson = "{\"eventName\":\"修改事件\",\"keyWord\":\"暂无\"}";
        mockMvc.perform(patch("/rs/3").content(newJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[2].eventName", is("修改事件")))
                .andExpect(jsonPath("$[2].keyWord", is("暂无")));
    }



}
