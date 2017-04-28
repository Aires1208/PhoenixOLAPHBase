package com.zte.ums.esight.controller;

import com.zte.ums.esight.Application;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class OlapDataControllerForRestTest {
    @Autowired
    private WebApplicationContext context;

//    @Mock
//    private GidService gidService;
//
//    @Mock
//    private TraceService traceService;
//
//    @Mock
//    private SwitchService switchService;
//
//    @InjectMocks
//    NFVTraceController nfvTraceController;


    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() throws Exception {
        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(nfvTraceController).build();
    }

    @Test
    public void should_call_getTraceSequence_when_rest_is_nfvtrace_gid_for_get() throws Exception{
//
//        List<Node> nodelist = new ArrayList<>();
//        List<Link> linkList = new ArrayList<>();
//        TraceSequence traceSequence = new TraceSequence(nodelist,linkList);
//
//        when(traceService.getTraceSequence("1", new Range(1, 2))).thenReturn(traceSequence);

        mockMvc.perform(get("/nfvtrace/gid/1")
                .param("from","1")
                .param("to","2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"class\":\"go.GraphLinksModel\",\"nodeDataArray\":[],\"linkDataArray\":[]}"));

    }


    @Test
    public void should_call_getGids_when_rest_is_nfvtrace_gid_for_get() throws Exception{

//        when(gidService.getGids(new Range(1,2))).thenReturn(newArrayList(new Gid("1")));

        mockMvc.perform(get("/nfvtrace/gid")
                .param("from","1")
                .param("to","2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":\"1\"}]"));
    }



    @Test
    public void should_call_changeSwitchStatus_when_rest_is_nfvtrace_switch_for_post()throws Exception{

//        when(switchService.changeSwitchStatus(anyObject())).
//                thenReturn(0);

        mockMvc.perform(post("/nfvtrace/switch")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("true").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":0}"));
    }

    @Test
    public void should_call_getSwitchStatus_when_rest_is_nfvtrace_switch_for_get() throws Exception{

//        when(switchService.getSwitchStatus()).thenReturn(0);

        mockMvc.perform(get("/nfvtrace/switch")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":0}"));

    }



}
