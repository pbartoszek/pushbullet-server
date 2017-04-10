package com.pushserver.push.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pushserver.Application;
import com.pushserver.push.scenario.SendPushCommand;
import com.pushserver.push.scenario.SendPushResponse;
import com.pushserver.push.scenario.SendPushScenario;
import com.pushserver.user.domain.User;
import com.pushserver.user.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {Application.class})
@TestPropertySource(properties = {"push.bullet.url=''"})
@DirtiesContext
public class PushControllerIT {

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private SendPushScenario sendPushScenario;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void shouldSendPush() throws Exception {
        //given
        String username = "bbcUser1";
        String pushId = "push1";
        int numOfPushes = 1;

        User user = new User(username, "token1");
        when(userRepository.getByUsername(username)).thenReturn(Optional.of(user));
        when(sendPushScenario.send(any(), eq(user))).thenReturn(new SendPushResponse(pushId, numOfPushes));
        //when
        sendPush(createSendPushCommand(username))
            //then
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.pushId").value(pushId))
            .andExpect(jsonPath("$.numOfNotificationsPushed").value(numOfPushes));
    }

    private SendPushCommand createSendPushCommand(String username) {
        SendPushCommand sendPushCommand = new SendPushCommand();
        sendPushCommand.setUsername(username);
        sendPushCommand.setBody("Body");
        sendPushCommand.setTitle("Title");
        return sendPushCommand;
    }

    private ResultActions sendPush(SendPushCommand sendPushCommand) throws Exception {
        return this.mockMvc.perform(post("/push")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(sendPushCommand)));
    }
}