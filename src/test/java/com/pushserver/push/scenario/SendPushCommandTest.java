package com.pushserver.push.scenario;

import com.pushserver.exception.ValidationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SendPushCommandTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void shouldPassValidationIfCommandValid() throws Exception {
        //given
        SendPushCommand command = createValidCommand();
        //when
        command.validate();
        //then - no exception thrown
    }

    @Test
    public void shouldThrowExceptionIfUsernameNull() throws Exception {
        //given
        SendPushCommand command = createValidCommand();
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
        SendPushCommand command = createValidCommand();
        command.setTitle(null);

        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("title");
        //when
        command.validate();
        //then - exception thrown
    }

    @Test
    public void shouldThrowExceptionIfTitleTooLong() throws Exception {
        //given
        SendPushCommand command = createValidCommand();
        String stringWith31Chars = Stream.generate(() -> "A").limit(31).collect(Collectors.joining());
        command.setTitle(stringWith31Chars);

        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("title");
        //when
        command.validate();
        //then - exception thrown
    }


    @Test
    public void shouldThrowExceptionIfBodyNull() throws Exception {
        //given
        SendPushCommand command = createValidCommand();
        command.setBody(null);

        expectedException.expect(ValidationException.class);
        expectedException.expectMessage("body");
        //when
        command.validate();
        //then - exception thrown
    }


    private SendPushCommand createValidCommand() {
        SendPushCommand command = new SendPushCommand();
        command.setUsername("bbcUser");
        command.setBody("body");
        command.setTitle("title");
        return command;
    }
}