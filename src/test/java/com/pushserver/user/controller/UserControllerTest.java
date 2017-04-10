package com.pushserver.user.controller;

import com.pushserver.exception.ValidationException;
import com.pushserver.user.domain.InMemoryUserRepository;
import com.pushserver.user.domain.UserRepository;
import com.pushserver.user.scenario.RegisterUserCommand;
import com.pushserver.user.scenario.RegisterUserScenario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    UserRepository userRepository = new InMemoryUserRepository();
    UserVoConverter userVoConverter = new UserVoConverter();

    RegisterUserScenario registerUserScenario;
    UserController userController;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        registerUserScenario = new RegisterUserScenario(userRepository);
        userController = new UserController(userRepository, registerUserScenario, userVoConverter);
    }

    @Test
    public void shouldRegisterNewUser() throws Exception {
        //given
        String username = "bbcUser1";
        String token = "token1";
        int numOfPushes = 0;

        UserVo actual = registerUser(username, token);

        Assert.assertEquals(username, actual.getUsername());
        Assert.assertEquals(token, actual.getAccessToken());
        Assert.assertEquals(numOfPushes, actual.getNumOfNotificationsPushed());
        Assert.assertNotNull(actual.getCreationTime());
    }

    @Test
    public void shouldReturnAllRegisteredUsers() throws Exception {
        //given
        String username1 = "bbcUser1";
        String token1 = "token1";

        String username2 = "bbcUser2";
        String token2 = "token2";
        //when
        UserVo user1 = registerUser(username1, token1);
        UserVo user2 = registerUser(username2, token2);
        //then
        Collection<UserVo> actual = userController.getUsers();
        assertThat(actual).containsExactlyInAnyOrder(user1, user2);
    }

    @Test
    public void shouldReturnErrorResponseIfValidationFails() throws Exception {
        //given
        String username = "";
        String token = "token1";
        expectedException.expect(ValidationException.class);
        //when
        registerUser(username, token);
        //then - exception thrown
    }


    private UserVo registerUser(String username, String token) {
        RegisterUserCommand command = new RegisterUserCommand();
        command.setUsername(username);
        command.setAccessToken(token);

        return userController.createUser(command);
    }
}