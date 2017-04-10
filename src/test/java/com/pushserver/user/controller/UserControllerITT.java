package com.pushserver.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pushserver.Application;
import com.pushserver.push.scenario.SendPushScenario;
import com.pushserver.user.domain.UserRepository;
import com.pushserver.user.scenario.RegisterUserCommand;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {Application.class})
@TestPropertySource(properties = {"push.bullet.url=''"})
@DirtiesContext
public class UserControllerITT {

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
    public void shouldRegisterNewUser() throws Exception {
        //given
        String username = "bbcUser1";
        String token = "token1";

        //when
        registerNewUser(createRegisterUserCommand(username, token))
            //then
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.username").value(username))
            .andExpect(jsonPath("$.accessToken").value(token))
            .andExpect(jsonPath("$.numOfNotificationsPushed").value(0))
            .andExpect(jsonPath("$.creationTime").exists());
    }


    private RegisterUserCommand createRegisterUserCommand(String username, String token) {
        RegisterUserCommand registerUserCommand = new RegisterUserCommand();
        registerUserCommand.setUsername(username);
        registerUserCommand.setAccessToken(token);
        return registerUserCommand;
    }

    private ResultActions registerNewUser(RegisterUserCommand command) throws Exception {
        return this.mockMvc.perform(post("/users")
            .accept(MediaType.APPLICATION_JSON_UTF8)
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(command)));
    }
}