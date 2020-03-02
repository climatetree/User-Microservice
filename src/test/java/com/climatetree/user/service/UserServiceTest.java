package com.climatetree.user.service;

import com.climatetree.user.dto.Execution;
import com.climatetree.user.model.User;
import com.climatetree.user.enums.ResultEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void getUser() throws Exception {
        Execution<User> res = userService.getUser(10000L);
        Assertions.assertEquals(ResultEnum.SUCCESS, res.getResult());
        User user = res.getObject();
        Assertions.assertEquals(0, user.getUserId());
        Assertions.assertEquals("", user.getEmail());
        Assertions.assertEquals("", user.getNickname());
        Assertions.assertEquals(0, user.getRoleId());
        Assertions.assertEquals(true, user.getFlag());
    }

    @Test
    void getUsers()  {
        Execution<User> res = userService.findAllUsers();
        Assertions.assertEquals(ResultEnum.SUCCESS, res.getResult());
        List<User> users = res.getObjects();
        System.out.println(users);
        Assertions.assertEquals(users.size(), users.size());
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