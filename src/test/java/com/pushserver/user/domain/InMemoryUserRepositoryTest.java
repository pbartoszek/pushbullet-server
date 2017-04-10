package com.pushserver.user.domain;

import com.pushserver.user.domain.User;
import com.pushserver.user.domain.UserAlreadyExists;
import com.pushserver.user.domain.InMemoryUserRepository;
import com.pushserver.user.domain.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryUserRepositoryTest {

    private UserRepository userRepository;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        userRepository = new InMemoryUserRepository();
    }

    @Test
    public void shouldAddNewUserIfItDoesNotExists() {
        //given
        User user = new User("bbcUser1", "T1");
        //when
        userRepository.addUser(user);
        //then
        Optional<User> actual = userRepository.getByUsername("bbcUser1");
        Assert.assertEquals(user, actual.get());
    }

    @Test
    public void shouldThrowExceptionIfUsernameAlreadyRegistered() {
        //given
        String username = "bbcUser1";
        User user = new User(username, "T1");
        User otherUser = new User(username, "T2");
        userRepository.addUser(user);
        expectedException.expect(UserAlreadyExists.class);
        //when
        userRepository.addUser(otherUser);
        //then
        //exception thrown
    }

    @Test
    public void shouldThrowExceptionIfTokenAlreadyRegistered() {
        //given
        String token = "T1";
        User user = new User("bbcUser1", token);
        User otherUser = new User("bbcUser2", token);
        userRepository.addUser(user);
        expectedException.expect(UserAlreadyExists.class);
        //when
        userRepository.addUser(otherUser);
        //then
        //exception thrown
    }

    @Test
    public void shouldReturnAllRegisteredUsers() {
        //given
        User user = new User("bbcUser1", "T1");
        User otherUser = new User("bbcUser2", "T2");
        //when
        userRepository.addUser(user);
        userRepository.addUser(otherUser);
        //then
        assertThat(userRepository.getAll()).containsExactlyInAnyOrder(user, otherUser);
    }

    @Test
    public void shouldReturnEmptyCollectionIfNoUsersRegistered() {
        //then
        assertThat(userRepository.getAll()).isEmpty();
    }


    @Test
    public void shouldReturnEmptyOptionalForUnknownUsername() {
        //when
        Optional<User> actual = userRepository.getByUsername("someUsername");
        //then
        assertThat(actual).isEmpty();
    }

    @Test
    public void shouldReturnUserByUsername() {
        //given
        String username = "bbcUser1";
        User user = new User(username, "T1");
        //when
        userRepository.addUser(user);
        //then
        assertThat(userRepository.getByUsername(username)).isPresent();
    }
}
