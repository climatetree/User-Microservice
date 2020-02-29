package com.climatetree.user.dao;

import com.climatetree.user.model.User;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;



@SpringBootTest
@RunWith(SpringRunner.class)
class UserDaoTest {
    @Autowired
    private UserDao userDao;


    @Test
    void findAll() throws Exception{
        List<User> users = userDao.findAll();
        System.out.println(users);
        Assertions.assertEquals(0, users.size());
    }

    @Test
    void findByUserId() throws Exception{
        User testUser = userDao.findByUserId(1L);
        System.out.println(testUser);
        Assertions.assertEquals(0, testUser.getUserId());
        Assertions.assertEquals("", testUser.getEmail());
        Assertions.assertEquals(false, testUser.getFlag());
        Assertions.assertEquals("", testUser.getLastLoginLocation());
        Assertions.assertEquals("", testUser.getNickname());
        Assertions.assertEquals(0, testUser.getRoleId());

    }

    @Test
    void testInsertAndDelete() throws Exception{
        Long userId = 10000L;
        User user = new User();
        user.setEmail("");
        user.setFlag(true);
        user.setLastLoginLocation("");
        user.setLastLoginTime(new Date());
        user.setRegistrationDate(new Date());
        user.setNickname("");
        user.setRoleId(0);
        user.setUserId(userId);
        userDao.save(user);
        List<User> users = userDao.findAll();
        System.out.println(users);
        Assertions.assertEquals(0, users.size());
        User newUser = userDao.findByUserId(userId);
        Assertions.assertEquals(0, user.getRoleId());
        Assertions.assertEquals("", user.getEmail());
        Assertions.assertEquals(true, user.getFlag());
        Assertions.assertEquals("", user.getLastLoginLocation());
        Assertions.assertEquals(userId, user.getUserId());
        Assertions.assertEquals("", user.getNickname());
        userDao.deleteById(userId);
        users = userDao.findAll();
        System.out.println(users);
        Assertions.assertEquals(0, users.size());
    }


    @Test
    void testFindByName() throws Exception {
        List<User> users = userDao.findByNickname("");
        System.out.println(users);
        Assertions.assertEquals(0, users.size());
        User user = users.get(0);
        Assertions.assertEquals(0, user.getUserId());
        Assertions.assertEquals("", user.getNickname());
        Assertions.assertEquals("", user.getEmail());

    }

    @Test
    void testFindByRoleId() throws Exception {
        List<User> users = userDao.findByRoleId(1);
        System.out.println(users);
        Assertions.assertEquals(0, users.size());
        User user = users.get(0);
        Assertions.assertEquals(0, user.getUserId());
        Assertions.assertEquals("", user.getNickname());
        Assertions.assertEquals("", user.getEmail());

    }
}