package com.pushserver.push.controller;

import com.pushserver.exception.ValidationException;
import com.pushserver.push.scenario.SendPushCommand;
import com.pushserver.push.scenario.SendPushScenario;
import com.pushserver.user.domain.UserNotFound;
import com.pushserver.user.domain.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PushControllerTest {
    @Mock
    private SendPushScenario sendPushScenario;

    @Mock
    private UserRepository userRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private PushController pushController;

    @Before
    public void setUp() throws Exception {
        pushController = new PushController(userRepository, sendPushScenario);
    }

    @Test
    public void shouldThrowExceptionIfUsernameNotRegistered() throws Exception {
        //given
        String username = "XXX";
        when(userRepository.getByUsername(username)).thenReturn(Optional.empty());
        expectedException.expect(UserNotFound.class);
        //when
        pushController.sendPush(createSendPushCommand(username));
        //then - exception thrown
    }

    @Test
    public void shouldThrowExceptionIfValidationFails() throws Exception {
        //given
        String username = null;
        expectedException.expect(ValidationException.class);
        //when
        pushController.sendPush(createSendPushCommand(username));
        //then - exception thrown
    }


    private SendPushCommand createSendPushCommand(String username) {
        SendPushCommand sendPushCommand = new SendPushCommand();
        sendPushCommand.setUsername(username);
        sendPushCommand.setBody("Body");
        sendPushCommand.setTitle("Title");
        return sendPushCommand;
    }
}