package com.pushserver.user.domain;

import com.pushserver.user.domain.User;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void shouldTrimUsernameAndTokenWhenCreatingUser() throws Exception {
        //when
        User user = new User("  username    ", "  token    ");
        //then
        assertEquals("username", user.getUsername());
        assertEquals("token", user.getAccessToken());
    }

    @Test
    public void numOfPushesShouldBeZeroForNewUser() throws Exception {
        //when
        User user = new User("user", "token");
        //then
        assertEquals(0, user.getNumOfNotificationsPushed());
    }

    @Test
    public void numOfPushesShouldBeIncrementedWhenIncreaseNotificationsPushedCalled() throws Exception {
        //given
        User user = new User("user", "token");
        //when
        user.increaseNotificationsPushed();
        //then
        assertEquals(1, user.getNumOfNotificationsPushed());
    }

    @Test
    public void shouldReturnUserCreatedDateTime() throws Exception {
        //when
        User user = new User("  username    ", "  token    ");
        //then
        assertThat(user.getCreationTime()).isAfterOrEqualTo((LocalDateTime.now().minusSeconds(1)));
        assertThat(user.getCreationTime()).isBeforeOrEqualTo(LocalDateTime.now());
    }

}
