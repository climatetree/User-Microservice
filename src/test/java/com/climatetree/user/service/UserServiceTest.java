package com.climatetree.user.service;

import com.climatetree.user.dao.UserDao;
import com.climatetree.user.dto.Execution;
import com.climatetree.user.model.User;
import com.climatetree.user.enums.ResultEnum;
import java.util.Date;
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
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Test
    void getUser() throws Exception {
        Long userId = 20000L;
        User user = new User();
        user.setEmail("");
        user.setFlag(true);
        user.setLastLoginLocation("");
        user.setLastLoginTime(new Date());
        user.setRegistrationDate(new Date());
        user.setNickname("");
        user.setRoleId(1);
        user.setUserId(userId);
        userDao.save(user);

        Execution<User> res = userService.getUser(20000L);
        if(res!=null) {
            Assertions.assertEquals(ResultEnum.SUCCESS, res.getResult());

            Assertions.assertEquals(0, user.getUserId());
            Assertions.assertEquals("", user.getEmail());
            Assertions.assertEquals("", user.getNickname());
            Assertions.assertEquals(0, user.getRoleId());
            Assertions.assertEquals(true, user.getFlag());
        }
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