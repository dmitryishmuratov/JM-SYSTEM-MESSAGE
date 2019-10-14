package jm.controller.rest;

import com.google.gson.Gson;
import jm.ChannelService;
import jm.model.Channel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringRunner.class)

public class ChannelRestControllerTest {
    private static final String url = "/api/channels/";
    @Mock
    private ChannelService channelService;

    @InjectMocks
    ChannelRestController channelRestController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(channelRestController).build();
    }

    @Test
    public void getChannelById() throws Exception {
        Long testId1 = 1L;
        mockMvc.perform(get(url + testId1))
                .andExpect(status().isOk());
        verify(channelService, times(1)).getChannelById(testId1);

        String testId2 = "something_text";
        mockMvc.perform(get(url + testId2))
                .andExpect(status().isBadRequest());
        verify(channelService, times(1)).getChannelById(any());

        String testId3 = "something text";
        mockMvc.perform(get(url + testId3))
                .andExpect(status().isBadRequest());
        verify(channelService, times(1)).getChannelById(any());


        Channel channel = new Channel();
        channel.setId(2L);
        channel.setName("test");

        Long testId4 = 2L;
        when(channelService.getChannelById(testId4)).thenReturn(channel);

        MvcResult result = mockMvc.perform(get(url + testId4))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();
        verify(channelService, times(1)).getChannelById(testId4);

        Gson gson = new Gson();
        Channel resultChannel = gson.fromJson(result.getResponse().getContentAsString(), Channel.class);
        Assert.assertEquals(channel.getId(), resultChannel.getId());
        Assert.assertEquals(channel.getName(), resultChannel.getName());
    }

    @Test
    public void createChannel() throws Exception {
        Gson gson = new Gson();
        String jsonChannel;

        Channel channel = new Channel();
        jsonChannel = gson.toJson(channel);
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonChannel))
                .andExpect(status().isOk());
        verify(channelService, times(1)).createChannel(any());


        mockMvc.perform(post(url))
                .andExpect(status().isBadRequest());
        verify(channelService, times(1)).createChannel(any());


        channel = null;
        jsonChannel = gson.toJson(channel);
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonChannel))
                .andExpect(status().isBadRequest());
        verify(channelService, times(1)).createChannel(any());


        Object notChannelObject = "notChannelObject";
        jsonChannel = gson.toJson(notChannelObject);
        mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonChannel))
                .andExpect(status().isBadRequest());
        verify(channelService, times(1)).createChannel(any());

    }


    @Test
    public void updateChannel() throws Exception {
        Gson gson = new Gson();
        String jsonChannel;

        Channel channel = new Channel();
        jsonChannel = gson.toJson(channel);
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonChannel))
                .andExpect(status().isOk());
        verify(channelService, times(1)).updateChannel(any());


        mockMvc.perform(put(url))
                .andExpect(status().isBadRequest());
        verify(channelService, times(1)).updateChannel(any());


        channel = null;
        jsonChannel = gson.toJson(channel);
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonChannel))
                .andExpect(status().isBadRequest());
        verify(channelService, times(1)).updateChannel(any());


        Object notChannelObject = "notChannelObject";
        jsonChannel = gson.toJson(notChannelObject);
        mockMvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonChannel))
                .andExpect(status().isBadRequest());
        verify(channelService, times(1)).updateChannel(any());
    }

    @Test
    public void deleteChannel() throws Exception {
        Long testId1 = 1L;
        mockMvc.perform(delete(url + testId1))
                .andExpect(status().isOk());
        verify(channelService, times(1)).deleteChannel(testId1);

        String testId2 = "something_text";
        mockMvc.perform(delete(url + testId2))
                .andExpect(status().isBadRequest());
        verify(channelService, times(1)).deleteChannel(any());

        String testId3 = "something text";
        mockMvc.perform(delete(url + testId3))
                .andExpect(status().isBadRequest());
        verify(channelService, times(1)).deleteChannel(any());

    }

}