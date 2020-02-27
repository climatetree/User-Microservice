package com.CS6510.microservice.service;

import com.CS6510.microservice.dto.Execution;
import com.CS6510.microservice.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.CS6510.microservice.enums.ResultEnum.SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void getUser() throws Exception {
        Execution<User> res = userService.getUser(1L);
        Assertions.assertEquals(SUCCESS, res.getResult());
        User user = res.getObject();
        Assertions.assertEquals(1L, user.getUserId());
        Assertions.assertEquals("a@a.com", user.getEmail());
        Assertions.assertEquals("testGuest", user.getNickname());
        Assertions.assertEquals(1, user.getRoleId());
        Assertions.assertEquals(false, user.getFlag());
    }

    @Test
    void getUsers() throws Exception {
        Execution<User> res = userService.getUsers();
        Assertions.assertEquals(SUCCESS, res.getResult());
        List<User> users = res.getObjects();
        System.out.println(users);
        Assertions.assertEquals(2, users.size());
    }

    @Test
    void insertUser() {

    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUsersByName() {
    }

    @Test
    void getUsersByRoleId() {
    }
}