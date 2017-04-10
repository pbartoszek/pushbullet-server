package com.pushserver.user.scenario;

import com.pushserver.exception.ValidationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RegisterUserCommandTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldPassValidationIfCommandValid() throws Exception {
        //given
        RegisterUserCommand command = createValidCommand();
        //when
        command.validate();
        //then - no exception thrown
    }

    @Test
    public void shouldThrowExceptionIfUsernameNull() throws Exception {
        //given
        RegisterUserCommand command = createValidCommand();
        command.setUsername(null);

        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("username");
        //when
        command.validate();
        //then - exception thrown
    }

    @Test
    public void shouldThrowExceptionIfTitleNull() throws Exception {
        //given
        RegisterUserCommand command = createValidCommand();
        command.setAccessToken(null);

        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("accessToken");
        //when
        command.validate();
        //then - exception thrown
    }

    private RegisterUserCommand createValidCommand() {
        RegisterUserCommand command = new RegisterUserCommand();
        command.setUsername("bbcUser");
        command.setAccessToken("token1");
        return command;
    }
}