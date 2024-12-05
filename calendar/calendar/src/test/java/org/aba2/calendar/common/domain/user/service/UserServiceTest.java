package org.aba2.calendar.common.domain.user.service;

import org.aba2.calendar.common.domain.user.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    private UserEntity user;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setUserId("Test1");
        user.setPassword("aA123456$$$");
        user.setName("jihoon");
        user.setEmail("sjhoon1212@gmail.com");
        user.setPhoneNumber("01066268264");
        user.setBirthdate(LocalDateTime.parse("2001-09-25T00:00:00"));
        user.setCreateAt(LocalDateTime.now());
    }

    @Test
    void register() {
        UserEntity savedUser = userService.register(user);
        assertNotNull(savedUser);
        assertEquals(user.getUserId(), savedUser.getUserId());
        assertEquals(user.getPassword(), savedUser.getPassword());
    }

    @Test
    void login() {
        UserEntity savedUser = userService.login(user.getUserId(), user.getPassword());
        assertNotNull(savedUser);
        assertEquals(user.getUserId(), savedUser.getUserId());
        assertEquals(user.getPassword(), savedUser.getPassword());
    }
}