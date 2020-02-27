package com.CS6510.microservice.dao;

import com.CS6510.microservice.model.User;

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
        Assertions.assertEquals(2, users.size());
    }

    @Test
    void findByUserId() throws Exception{
        User testUser = userDao.findByUserId(1L);
        System.out.println(testUser);
        Assertions.assertEquals(1L, testUser.getUserId());
        Assertions.assertEquals("a@a.com", testUser.getEmail());
        Assertions.assertEquals(false, testUser.getFlag());
        Assertions.assertEquals("Seattle", testUser.getLastLoginLocation());
        Assertions.assertEquals("testGuest", testUser.getNickname());
        Assertions.assertEquals(1, testUser.getRoleId());

    }

    @Test
    void testInsertAndDelete() throws Exception{
        Long userId = 10000L;
        User user = new User();
        user.setEmail("testEmail");
        user.setFlag(true);
        user.setLastLoginLocation("test");
        user.setLastLoginTime(new Date());
        user.setRegistrationDate(new Date());
        user.setNickname("testUser");
        user.setRoleId(1);
        user.setUserId(userId);
        userDao.save(user);
        List<User> users = userDao.findAll();
        System.out.println(users);
        Assertions.assertEquals(3, users.size());
        User newUser = userDao.findByUserId(userId);
        Assertions.assertEquals(1, user.getRoleId());
        Assertions.assertEquals("testEmail", user.getEmail());
        Assertions.assertEquals(true, user.getFlag());
        Assertions.assertEquals("test", user.getLastLoginLocation());
        Assertions.assertEquals(userId, user.getUserId());
        Assertions.assertEquals("testUser", user.getNickname());
        userDao.deleteById(userId);
        users = userDao.findAll();
        System.out.println(users);
        Assertions.assertEquals(2, users.size());
    }


    @Test
    void testFindByName() throws Exception {
        List<User> users = userDao.findByNickname("testGuest");
        System.out.println(users);
        Assertions.assertEquals(1, users.size());
        User user = users.get(0);
        Assertions.assertEquals(1L, user.getUserId());
        Assertions.assertEquals("testGuest", user.getNickname());
        Assertions.assertEquals("a@a.com", user.getEmail());

    }

    @Test
    void testFindByRoleId() throws Exception {
        List<User> users = userDao.findByRoleId(1);
        System.out.println(users);
        Assertions.assertEquals(1, users.size());
        User user = users.get(0);
        Assertions.assertEquals(1L, user.getUserId());
        Assertions.assertEquals("testGuest", user.getNickname());
        Assertions.assertEquals("a@a.com", user.getEmail());

    }
}