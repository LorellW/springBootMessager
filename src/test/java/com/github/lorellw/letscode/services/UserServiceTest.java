package com.github.lorellw.letscode.services;

import com.github.lorellw.letscode.entiites.Role;
import com.github.lorellw.letscode.entiites.User;
import com.github.lorellw.letscode.repositories.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService service;
    @MockBean
    private UserRepository repository;
    @MockBean
    private MailSenderService sender;
    @MockBean
    private PasswordEncoder encoder;

    @Test
    void addUser() {
        User user = new User();
        user.setEmail("some@mail.com");

        boolean isUserCreated = service.addUser(user);

        assertTrue(isUserCreated);
        assertNotNull(user.getActivationCode());
        assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));

        Mockito.verify(repository, Mockito.times(1)).save(user);
        Mockito.verify(sender, Mockito.times(1)).send(
                ArgumentMatchers.eq(user.getEmail()),
                ArgumentMatchers.eq("Activation code"),
                ArgumentMatchers.contains("Activation code"));
    }

    @Test
    void addUserFailedTest(){
        User user = new User();
        user.setUsername("John Doe");

        Mockito.doReturn(new User())
                .when(repository)
                .findByUsername("John Doe");

        boolean isUserCreated = service.addUser(user);

        assertFalse(isUserCreated);

        Mockito.verify(repository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
        Mockito.verify(sender, Mockito.times(0)).send(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString());
    }


    @Test
    void activateUserTest(){
        User user = new User();
        user.setActivationCode("qwerty");

        Mockito.doReturn(user)
                .when(repository)
                .findByActivationCode("activate");

        boolean isActivated = service.activateUser("activate");
        assertTrue(isActivated);
        assertNull(user.getActivationCode());

        Mockito.verify(repository, Mockito.times(1)).save(user);
    }

    @Test
    void activateUserFailedTest(){
        boolean isActivated = service.activateUser("activate");
        assertFalse(isActivated);
        Mockito.verify(repository, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
        
    }
}